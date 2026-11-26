package ke.ac.kyu.campusgrade.client;

import ke.ac.kyu.campusgrade.api.GradeException;
import ke.ac.kyu.campusgrade.api.GradeService;
import ke.ac.kyu.campusgrade.common.Config;
import ke.ac.kyu.campusgrade.common.EventLog;

import java.net.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Client-side fault handling (6.4). Every remote call goes through here:
 * <ul>
 *   <li>the registry is looked up by name using configured host and port;</li>
 *   <li>each attempt has a timeout ({@code client.timeout.ms});</li>
 *   <li>a RemoteException, a NotBoundException or a timeout is logged with a timestamp,
 *       the stub is dropped (the server may have moved or restarted) and the call is
 *       retried with exponential back-off, up to {@code client.retries} times;</li>
 *   <li>application errors (GradeException) are never retried.</li>
 * </ul>
 * Retrying is only safe because callers reuse one request ID for every
 * attempt of a submitMark.
 */
public final class RetryingCaller implements AutoCloseable {

    /** One remote operation. */
    @FunctionalInterface
    public interface RemoteCall<T> {
        T call(GradeService service) throws RemoteException, GradeException;
    }

    /** Thrown when every attempt failed. */
    public static final class GaveUpException extends Exception {
        private static final long serialVersionUID = 1L;

        GaveUpException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    private final String host;
    private final int port;
    private final long timeoutMillis;
    private final int retries;
    private final long backoffMillis;
    private final EventLog log;
    private final ExecutorService callers = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r, "rmi-call");
        t.setDaemon(true);
        return t;
    });
    private volatile GradeService cached;

    public RetryingCaller(EventLog log) {
        this(Config.registryHost(), Config.registryPort(),
                Config.getLong(Config.CLIENT_TIMEOUT_MS, 5_000),
                Config.getInt(Config.CLIENT_RETRIES, 3),
                Config.getLong(Config.CLIENT_BACKOFF_MS, 250), log);
    }

    public RetryingCaller(String host, int port, long timeoutMillis, int retries, long backoffMillis, EventLog log) {
        this.host = host;
        this.port = port;
        this.timeoutMillis = timeoutMillis;
        this.retries = retries;
        this.backoffMillis = backoffMillis;
        this.log = log;
    }

    public GradeService lookup() throws RemoteException, NotBoundException {
        GradeService s = cached;
        if (s == null) {
            Registry reg = LocateRegistry.getRegistry(host, port);
            s = (GradeService) reg.lookup(GradeService.SERVICE_NAME);
            cached = s;
        }
        return s;
    }

    public <T> T call(String what, RemoteCall<T> op) throws GradeException, GaveUpException {
        Throwable last = null;
        int attempts = retries + 1;
        for (int attempt = 1; attempt <= attempts; attempt++) {
            Future<T> f = callers.submit(() -> op.call(lookup()));
            try {
                T result = f.get(timeoutMillis, TimeUnit.MILLISECONDS);
                if (attempt > 1) {
                    log.info(what + " succeeded on attempt " + attempt + "/" + attempts);
                }
                return result;
            } catch (TimeoutException e) {
                f.cancel(true);
                last = e;
                log.failure(what + " attempt " + attempt + "/" + attempts + " timed out after "
                        + timeoutMillis + " ms (server may still have executed it)");
            } catch (ExecutionException e) {
                Throwable c = e.getCause();
                if (c instanceof GradeException ge) {
                    throw ge;                   // the server answered: do not retry
                }
                last = c;
                log.failure(what + " attempt " + attempt + "/" + attempts + " failed ["
                        + classify(c) + "]", c);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new GaveUpException(what + " interrupted", e);
            }
            cached = null;                       // re-lookup on the next attempt
            if (attempt < attempts) {
                sleep(backoffMillis * (1L << (attempt - 1)));
            }
        }
        log.failure(what + " gave up after " + attempts + " attempts");
        throw new GaveUpException(what + " failed after " + attempts + " attempts", last);
    }

    /** Says whether the failed call can have reached the server. */
    static String classify(Throwable t) {
        Throwable root = t;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        if (t instanceof NotBoundException) {
            return "service not bound: not executed";
        }
        if (t instanceof java.rmi.ConnectException || root instanceof ConnectException) {
            return "connection refused: not executed";
        }
        if (t instanceof java.rmi.UnknownHostException) {
            return "unknown host: not executed";
        }
        if (t instanceof java.rmi.UnmarshalException || t instanceof java.rmi.ConnectIOException) {
            return "connection lost during call: may have executed";
        }
        return "remote failure: may have executed";
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void close() {
        callers.shutdownNow();
    }
}
