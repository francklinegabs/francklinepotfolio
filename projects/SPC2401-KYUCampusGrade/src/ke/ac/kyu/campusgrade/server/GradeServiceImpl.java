package ke.ac.kyu.campusgrade.server;

import ke.ac.kyu.campusgrade.api.GradeService;
import ke.ac.kyu.campusgrade.api.InvalidMarkException;
import ke.ac.kyu.campusgrade.api.Lease;
import ke.ac.kyu.campusgrade.api.MarkNotPublishedException;
import ke.ac.kyu.campusgrade.api.MarkRecord;
import ke.ac.kyu.campusgrade.api.NotSubscribedException;
import ke.ac.kyu.campusgrade.api.StudentListener;
import ke.ac.kyu.campusgrade.api.UnknownStudentException;
import ke.ac.kyu.campusgrade.api.UnknownUnitException;
import ke.ac.kyu.campusgrade.common.EventLog;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.LongSupplier;

/**
 * The GradeService remote object.
 *
 * <p>Design rules that answer Section 4:
 * <ul>
 *   <li>No method is {@code synchronized} on this object. Shared state lives in
 *       {@link MarkStore} (read/write lock), {@link SubscriptionManager} and
 *       {@link RequestDeduplicator} (concurrent maps).</li>
 *   <li>publishMarks takes a snapshot of the subscribers, releases every lock,
 *       and hands the callbacks to {@link Notifier}. A student whose
 *       markPublished() calls getMark() therefore cannot deadlock (4.2 fix).</li>
 *   <li>submitMark with a request ID is applied at most once, even across a
 *       crash, because the ID is journaled with the mark.</li>
 * </ul>
 */
public final class GradeServiceImpl implements GradeService, AutoCloseable {

    private final MarkStore store;
    private final SubscriptionManager subscriptions;
    private final RequestDeduplicator dedup;
    private final Notifier notifier;
    private final WorkerPool workers;
    private final Journal journal;
    private final EventLog log;
    private final ServerSettings settings;
    private final LongSupplier clock;
    private final ScheduledExecutorService housekeeping;
    /** Keeps the journal order identical to the in-memory order of writes. */
    private final Object applyLock = new Object();

    private volatile CompletableFuture<Notifier.PublishReport> lastPublication =
            CompletableFuture.completedFuture(null);

    public GradeServiceImpl(ServerSettings settings, EventLog log) throws IOException {
        this(settings, log, System::currentTimeMillis);
    }

    public GradeServiceImpl(ServerSettings settings, EventLog log, LongSupplier clock) throws IOException {
        this.settings = settings;
        this.log = log;
        this.clock = clock;
        this.store = new MarkStore();
        this.subscriptions = new SubscriptionManager(settings.defaultLeaseMillis, settings.maxLeaseMillis, clock);
        this.dedup = new RequestDeduplicator(settings.dedupWindowMillis, clock);
        this.notifier = new Notifier(settings.notifierThreads, subscriptions, log);
        this.workers = new WorkerPool(settings.workers, settings.simCpuMillis, settings.simDiskMillis,
                settings.simCores);
        this.journal = Journal.open(settings.journalFile);
        recover();
        this.housekeeping = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "housekeeping");
            t.setDaemon(true);
            return t;
        });
        long period = Math.max(50, Math.min(5_000, settings.defaultLeaseMillis / 4));
        housekeeping.scheduleAtFixedRate(this::housekeep, period, period, TimeUnit.MILLISECONDS);
    }

    // ------------------------------------------------------------------ submit

    @Override
    public void submitMark(String studentId, String unitCode, int mark)
            throws RemoteException, InvalidMarkException {
        submitMark(null, studentId, unitCode, mark);
    }

    @Override
    public void submitMark(String requestId, String studentId, String unitCode, int mark)
            throws RemoteException, InvalidMarkException {
        Validation.requestId(requestId);
        Validation.studentId(studentId);
        Validation.unitCode(unitCode);
        try {
            Validation.mark(mark);
        } catch (InvalidMarkException e) {
            log.warn("rejected submitMark(" + studentId + ", " + unitCode + ", " + mark + "): " + e.getMessage());
            throw e;
        }
        try {
            workers.execute(() -> {
                applySubmit(requestId, studentId, unitCode, mark);
                return null;
            });
        } catch (InvalidMarkException | RemoteException e) {
            throw e;
        } catch (ke.ac.kyu.campusgrade.api.GradeException e) {
            throw new RemoteException("unexpected", e);
        }
    }

    private void applySubmit(String requestId, String studentId, String unitCode, int mark)
            throws InvalidMarkException {
        if (requestId != null) {
            while (true) {
                RequestDeduplicator.Ticket t = dedup.begin(requestId);
                if (t.first()) {
                    break;
                }
                try {
                    if (t.awaitOriginal(settings.duplicateWaitMillis)) {
                        log.info("duplicate submitMark request " + requestId + " (" + studentId + ", " + unitCode
                                + ") ignored: already applied");
                        return;
                    }
                    // original failed before applying: loop and try to claim the ID ourselves
                } catch (TimeoutException e) {
                    throw new InvalidMarkException("Request " + requestId + " is still in progress; retry later");
                }
            }
        }
        try {
            long now = clock.getAsLong();
            int previous;
            synchronized (applyLock) {                 // short lock, never held during a callback
                journal.appendMark(requestId, studentId, unitCode, mark, now);
                previous = store.put(studentId, unitCode, mark, now);
            }
            if (requestId != null) {
                dedup.complete(requestId);
            }
            log.info("submitMark(" + studentId + ", " + unitCode + ", " + mark + ") applied"
                    + (requestId == null ? "" : " req=" + requestId)
                    + (previous >= 0 ? " (replaced " + previous + ")" : ""));
        } catch (IOException | RuntimeException e) {
            if (requestId != null) {
                dedup.abort(requestId, e);
            }
            log.failure("submitMark " + studentId + "/" + unitCode + " could not be stored", e);
            throw new IllegalStateException("store failure: " + e.getMessage(), e);
        }
        sleepQuietly(settings.simPostApplyMillis);    // used only by experiment 7.3-A2
    }

    // --------------------------------------------------------------------- get

    @Override
    public int getMark(String studentId, String unitCode)
            throws RemoteException, UnknownStudentException, MarkNotPublishedException {
        if (!Validation.isValidId(studentId) || !Validation.isValidId(unitCode)) {
            throw new UnknownStudentException("No mark for '" + studentId + "' in '" + unitCode + "'");
        }
        try {
            return workers.execute(() -> {
                OptionalInt m = store.get(studentId, unitCode);
                if (m.isEmpty()) {
                    throw new UnknownStudentException("No mark for " + studentId + " in " + unitCode);
                }
                if (!store.isPublished(unitCode)) {
                    throw new MarkNotPublishedException(unitCode + " has not been published yet");
                }
                return m.getAsInt();
            });
        } catch (UnknownStudentException | MarkNotPublishedException | RemoteException e) {
            throw e;
        } catch (ke.ac.kyu.campusgrade.api.GradeException e) {
            throw new RemoteException("unexpected", e);
        }
    }

    // ----------------------------------------------------------------- publish

    @Override
    public void publishMarks(String unitCode) throws RemoteException, UnknownUnitException {
        if (!Validation.isValidId(unitCode)) {
            throw new UnknownUnitException("Invalid unit code '" + unitCode + "'");
        }
        List<SubscriptionManager.Subscription> targets;
        synchronized (applyLock) {
            MarkStore.PublishResult r = store.publish(unitCode);
            if (r == MarkStore.PublishResult.UNKNOWN_UNIT) {
                throw new UnknownUnitException("No marks have been submitted for " + unitCode);
            }
            if (r == MarkStore.PublishResult.ALREADY_PUBLISHED) {
                log.info("publishMarks(" + unitCode + ") repeated with no changes: nothing to notify");
                return;
            }
            try {
                journal.appendPublish(unitCode, clock.getAsLong());
            } catch (IOException e) {
                log.failure("journal write for publish " + unitCode + " failed", e);
            }
            targets = subscriptions.activeFor(store.studentsIn(unitCode));
        }
        // Every lock is released here. The callbacks run on the notifier pool,
        // so this RMI thread returns to the lecturer straight away.
        log.info("publishMarks(" + unitCode + "): notifying " + targets.size() + " subscribed student(s)");
        lastPublication = notifier.publish(unitCode, targets);
    }

    // ------------------------------------------------------------ subscription

    @Override
    public void subscribe(String studentId, StudentListener listener)
            throws RemoteException, InvalidMarkException {
        subscribe(studentId, listener, 0);
    }

    @Override
    public Lease subscribe(String studentId, StudentListener listener, long requestedLeaseMillis)
            throws RemoteException, InvalidMarkException {
        Validation.studentId(studentId);
        if (listener == null) {
            throw new InvalidMarkException("listener must not be null");
        }
        Lease lease = subscriptions.subscribe(studentId, listener, requestedLeaseMillis);
        log.info("subscribe(" + studentId + ") granted " + lease.getDurationMillis() + " ms lease");
        return lease;
    }

    @Override
    public void unsubscribe(String studentId) throws RemoteException {
        boolean removed = subscriptions.unsubscribe(studentId);
        log.info("unsubscribe(" + studentId + ")" + (removed ? "" : " - was not subscribed"));
    }

    @Override
    public Lease renewLease(String studentId) throws RemoteException, NotSubscribedException {
        try {
            return subscriptions.renew(studentId);
        } catch (NotSubscribedException e) {
            log.warn("renewLease(" + studentId + ") refused: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<MarkRecord> listMarks(String unitCode) throws RemoteException {
        return store.list(unitCode);
    }

    // ---------------------------------------------------------------- internal

    private void recover() throws IOException {
        int n = journal.replay(new Journal.Replayer() {
            @Override
            public void mark(String requestId, String studentId, String unitCode, int mark, long when) {
                store.put(studentId, unitCode, mark, when);
                if (requestId != null) {
                    dedup.recordCompleted(requestId, when);
                }
            }

            @Override
            public void publish(String unitCode, long when) {
                store.publish(unitCode);
            }
        });
        if (n > 0) {
            log.info("recovered " + n + " journal entries: " + store.size() + " marks, "
                    + dedup.size() + " remembered request IDs");
        }
    }

    /** Reaps expired leases and old request IDs. Package-private for tests. */
    void housekeep() {
        try {
            for (String sid : subscriptions.reapExpired()) {
                log.info("lease expired for " + sid + "; subscription removed");
            }
            dedup.purgeExpired();
        } catch (RuntimeException e) {
            log.failure("housekeeping failed", e);
        }
    }

    private static void sleepQuietly(long ms) {
        if (ms > 0) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Accessors for tests and the benchmark.
    MarkStore store() { return store; }
    SubscriptionManager subscriptions() { return subscriptions; }
    RequestDeduplicator dedup() { return dedup; }

    public CompletableFuture<Notifier.PublishReport> lastPublication() {
        return lastPublication;
    }

    public long submittedCount() {
        return store.submittedCount();
    }

    @Override
    public void close() {
        housekeeping.shutdownNow();
        notifier.close();
        workers.close();
        try {
            journal.close();
        } catch (IOException e) {
            log.failure("journal close failed", e);
        }
    }
}
