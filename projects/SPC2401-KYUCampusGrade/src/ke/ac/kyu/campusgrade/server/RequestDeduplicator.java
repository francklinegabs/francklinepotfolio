package ke.ac.kyu.campusgrade.server;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.LongSupplier;

/**
 * Remembers request IDs so that a retried submitMark is applied at most once.
 *
 * <p>This is the "server must remember" column of 1.2: a table of request IDs
 * seen in the last {@code dedup.window.ms}. A duplicate that arrives while the
 * original is still running waits for the original instead of returning early,
 * so a client is never told "stored" before the mark really is stored.
 */
public final class RequestDeduplicator {

    /** Result of {@link #begin}: either we run the request, or someone already did. */
    public record Ticket(boolean first, CompletableFuture<Void> done) {
        /** Waits for the original call. Returns false if it failed and the caller should run it. */
        public boolean awaitOriginal(long timeoutMillis) throws TimeoutException {
            try {
                done.get(timeoutMillis, TimeUnit.MILLISECONDS);
                return true;
            } catch (java.util.concurrent.ExecutionException e) {
                return false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
    }

    private record Entry(CompletableFuture<Void> done, long createdMillis) { }

    private final Map<String, Entry> seen = new ConcurrentHashMap<>();
    private final long windowMillis;
    private final LongSupplier clock;

    public RequestDeduplicator(long windowMillis, LongSupplier clock) {
        this.windowMillis = windowMillis;
        this.clock = clock;
    }

    /** Atomically claims a request ID. Exactly one caller per ID gets first=true. */
    public Ticket begin(String requestId) {
        Entry fresh = new Entry(new CompletableFuture<>(), clock.getAsLong());
        Entry existing = seen.putIfAbsent(requestId, fresh);
        return existing == null ? new Ticket(true, fresh.done) : new Ticket(false, existing.done);
    }

    public void complete(String requestId) {
        Entry e = seen.get(requestId);
        if (e != null) {
            e.done.complete(null);
        }
    }

    /** The original failed before applying: forget it so a retry can run. */
    public void abort(String requestId, Throwable cause) {
        Entry e = seen.remove(requestId);
        if (e != null) {
            e.done.completeExceptionally(cause);
        }
    }

    /** Used when replaying the journal after a restart. */
    public void recordCompleted(String requestId, long whenMillis) {
        CompletableFuture<Void> f = CompletableFuture.completedFuture(null);
        seen.put(requestId, new Entry(f, whenMillis));
    }

    public boolean contains(String requestId) {
        return seen.containsKey(requestId);
    }

    /** Drops completed IDs older than the window. Returns how many were dropped. */
    public int purgeExpired() {
        long cutoff = clock.getAsLong() - windowMillis;
        int before = seen.size();
        seen.entrySet().removeIf(en -> en.getValue().done.isDone() && en.getValue().createdMillis < cutoff);
        return before - seen.size();
    }

    public int size() {
        return seen.size();
    }
}
