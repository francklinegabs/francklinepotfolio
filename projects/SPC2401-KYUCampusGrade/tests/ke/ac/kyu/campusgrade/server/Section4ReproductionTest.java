package ke.ac.kyu.campusgrade.server;

import ke.ac.kyu.campusgrade.api.StudentListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Reproduces the two Section 4 defects with the ORIGINAL code, then shows the
 * implementation does not have them.
 *
 * <p>RMI runs each incoming call on its own thread. The listener below copies
 * that: its getMark() runs on a different thread, and markPublished() waits
 * for the reply, just as a real remote callback waits for its network reply.
 */
class Section4ReproductionTest {

    /** The class from Section 4, unchanged apart from a listeners map so it compiles. */
    static class OriginalGradeServiceImpl {
        private final Map<String, Integer> marks = new HashMap<>();
        private int submitted = 0;
        final Map<String, List<StudentListener>> listeners = new HashMap<>();

        public void submitMark(String sid, String unit, int mark) {
            int old = submitted;           // (1)
            marks.put(sid + unit, mark);   // (2)
            submitted = old + 1;           // (3)
        }

        public synchronized int getMark(String sid, String unit) {
            return marks.get(sid + unit);
        }

        public synchronized void publishMarks(String unit) throws Exception {
            for (StudentListener l : listeners.get(unit)) {
                l.markPublished(unit);     // remote callback while holding the lock
            }
        }
    }

    /** Student callback that calls getMark through "another RMI thread" before returning. */
    static StudentListener callsBack(java.util.function.IntSupplier getMarkOnServer,
                                     AtomicReference<Thread> rmiThread) {
        return unit -> {
            Thread t = new Thread(getMarkOnServer::getAsInt, "RMI TCP Connection(sim)");
            t.setDaemon(true);             // a deadlocked thread must not keep the JVM alive
            rmiThread.set(t);
            t.start();
            try {
                t.join();                  // markPublished does not return until getMark replies
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    @Test
    @DisplayName("D01 original code: callback -> getMark DEADLOCKS (getMark thread stays BLOCKED)")
    void originalDeadlocks() throws Exception {
        OriginalGradeServiceImpl orig = new OriginalGradeServiceImpl();
        orig.submitMark("KYU001", "SPC2401", 78);
        AtomicReference<Thread> rmi = new AtomicReference<>();
        orig.listeners.put("SPC2401", List.of(callsBack(() -> orig.getMark("KYU001", "SPC2401"), rmi)));

        Thread publisher = new Thread(() -> {
            try {
                orig.publishMarks("SPC2401");
            } catch (Exception ignored) {
                // interrupted at the end of the test
            }
        }, "lecturer-publish");
        publisher.setDaemon(true);
        publisher.start();
        // Wait (up to 10 s, even on a slow first start) until the getMark thread is BLOCKED.
        long deadline = System.currentTimeMillis() + 10_000;
        while (System.currentTimeMillis() < deadline
                && (rmi.get() == null || rmi.get().getState() != Thread.State.BLOCKED)) {
            Thread.sleep(20);
        }
        Thread getter = rmi.get();
        assertTrue(getter != null, "the callback never started");
        assertEquals(Thread.State.BLOCKED, getter.getState(),
                "getMark waits for the lock that publishMarks holds; publishMarks waits for getMark");
        publisher.join(2_000);                     // give it 2 more seconds: it still cannot finish
        assertTrue(publisher.isAlive(), "publishMarks should still be stuck");
        assertEquals(Thread.State.BLOCKED, getter.getState());
        System.out.println("[D01] after 2 s: publisher=" + publisher.getState()
                + " (waiting in join), getMark thread=" + getter.getState() + " => DEADLOCK");
    }

    @Test
    @DisplayName("D02 fixed implementation: the same callback pattern completes")
    void fixedDoesNotDeadlock() throws Exception {
        try (GradeServiceImpl svc = TestSupport.service()) {
            svc.submitMark("KYU001", "SPC2401", 78);
            AtomicReference<Thread> rmi = new AtomicReference<>();
            AtomicInteger seen = new AtomicInteger(-1);
            svc.subscribe("KYU001", callsBack(() -> {
                try {
                    seen.set(svc.getMark("KYU001", "SPC2401"));
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
                return seen.get();
            }, rmi));
            long t0 = System.nanoTime();
            svc.publishMarks("SPC2401");             // returns at once: callbacks are on the notifier pool
            Notifier.PublishReport r = svc.lastPublication().get(5, TimeUnit.SECONDS);
            long ms = (System.nanoTime() - t0) / 1_000_000;
            assertEquals(1, r.delivered());
            assertEquals(78, seen.get());
            System.out.println("[D02] callback fetched mark " + seen.get() + " in " + ms + " ms: no deadlock");
        }
    }

    @Test
    @DisplayName("D03 lost update: original counter vs fixed store, 20 threads x 5,000 submits")
    void lostUpdate() throws Exception {
        int threads = 20;
        int each = 5_000;
        OriginalGradeServiceImpl orig = new OriginalGradeServiceImpl();
        run(threads, each, (t, i) -> {
            try {
                orig.submitMark("S" + t + "_" + i, "SPC2401", 50);
            } catch (RuntimeException ignored) {
                // HashMap may even throw when used by many threads
            }
        });
        MarkStore fixed = new MarkStore();
        run(threads, each, (t, i) -> fixed.put("S" + t + "_" + i, "SPC2401", 50, 0));

        int expected = threads * each;
        System.out.println("[D03] expected " + expected + " | original submitted=" + orig.submitted
                + " (lost " + (expected - orig.submitted) + "), map size=" + orig.marks.size()
                + " | fixed submitted=" + fixed.submittedCount() + ", size=" + fixed.size());
        assertEquals(expected, fixed.submittedCount());
        assertEquals(expected, fixed.size());
        assertFalse(orig.submitted > expected);
    }

    private interface Op {
        void apply(int thread, int i);
    }

    private static void run(int threads, int each, Op op) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch go = new CountDownLatch(1);
        List<Future<?>> fs = new ArrayList<>();
        for (int t = 0; t < threads; t++) {
            int id = t;
            fs.add(pool.submit(() -> {
                go.await();
                for (int i = 0; i < each; i++) {
                    op.apply(id, i);
                }
                return null;
            }));
        }
        go.countDown();
        for (Future<?> f : fs) {
            f.get(60, TimeUnit.SECONDS);
        }
        pool.shutdown();
    }
}
