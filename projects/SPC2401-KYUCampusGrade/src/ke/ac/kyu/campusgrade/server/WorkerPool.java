package ke.ac.kyu.campusgrade.server;

import ke.ac.kyu.campusgrade.api.GradeException;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Worker-pool thread architecture chosen in 3.3.
 *
 * <p>RMI accepts each call on one of its own connection threads; that thread
 * hands the request to a fixed pool of {@code server.workers} threads and
 * waits. The pool size caps how many requests run at once, so a burst of
 * 2,000 students cannot create 2,000 runnable threads.
 *
 * <p>An optional simulated cost (4 ms CPU + 6 ms disk wait, as in 4.3) is used
 * by the 7.4 benchmark. {@code sim.cores=1} makes the CPU part run on one
 * simulated core so the measurements can be compared with the single-core
 * calculation.
 */
public final class WorkerPool implements AutoCloseable {

    /** A unit of server work that may fail with an application error. */
    @FunctionalInterface
    public interface Task<T> {
        T run() throws GradeException;
    }

    private final ExecutorService pool;   // null = run on the RMI thread itself
    private final long cpuNanos;
    private final long diskMillis;
    private final Semaphore cores;        // null = no simulated core limit

    public WorkerPool(int workers, long cpuMillis, long diskMillis, int simulatedCores) {
        this.cpuNanos = cpuMillis * 1_000_000L;
        this.diskMillis = diskMillis;
        this.cores = simulatedCores > 0 ? new Semaphore(simulatedCores, true) : null;
        if (workers > 0) {
            AtomicInteger n = new AtomicInteger();
            ThreadFactory tf = r -> {
                Thread t = new Thread(r, "worker-" + n.incrementAndGet());
                t.setDaemon(true);
                return t;
            };
            this.pool = Executors.newFixedThreadPool(workers, tf);
        } else {
            this.pool = null;
        }
    }

    public <T> T execute(Task<T> task) throws GradeException, RemoteException {
        if (pool == null) {
            simulateCost();
            return task.run();
        }
        Future<T> f = pool.submit(() -> {
            simulateCost();
            return task.run();
        });
        try {
            return f.get();
        } catch (InterruptedException e) {
            f.cancel(true);
            Thread.currentThread().interrupt();
            throw new RemoteException("server interrupted", e);
        } catch (ExecutionException e) {
            Throwable c = e.getCause();
            if (c instanceof GradeException ge) {
                throw ge;
            }
            if (c instanceof RuntimeException re) {
                throw re;
            }
            throw new RemoteException("server error", c);
        }
    }

    private void simulateCost() throws GradeException {
        if (cpuNanos > 0) {
            if (cores != null) {
                cores.acquireUninterruptibly();
            }
            try {
                long end = System.nanoTime() + cpuNanos;
                while (System.nanoTime() < end) {
                    Thread.onSpinWait();     // busy CPU, like real computation
                }
            } finally {
                if (cores != null) {
                    cores.release();
                }
            }
        }
        if (diskMillis > 0) {
            try {
                Thread.sleep(diskMillis);     // blocked on I/O: the CPU is free
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void close() {
        if (pool != null) {
            pool.shutdownNow();
        }
    }
}
