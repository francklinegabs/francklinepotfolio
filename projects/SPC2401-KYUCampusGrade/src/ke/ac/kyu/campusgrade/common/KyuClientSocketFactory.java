package ke.ac.kyu.campusgrade.common;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

/**
 * Client socket factory attached to every exported object (the GradeService
 * and each StudentListener). RMI serialises it inside the stub, so it runs in
 * the JVM that MAKES the call and reads that JVM's configuration:
 * <ul>
 *   <li>{@code socket.timeout.ms}: connect and read timeout, so a dead peer
 *       cannot block a caller (or a notifier thread) forever;</li>
 *   <li>{@code net.delay.ms}: added before every request leaves this JVM, to
 *       simulate a slow network in the 7.3 experiments.</li>
 * </ul>
 */
public final class KyuClientSocketFactory implements RMIClientSocketFactory, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        int timeout = Config.getInt(Config.SOCKET_TIMEOUT_MS, 15_000);
        long delay = Config.getLong(Config.NET_DELAY_MS, 0);
        Socket s = delay > 0 ? new DelayedSocket(delay) : new Socket();
        s.connect(new InetSocketAddress(host, port), timeout);
        s.setSoTimeout(timeout);
        s.setTcpNoDelay(true);
        return s;
    }

    // RMI compares factories to decide whether connections can be shared.
    @Override
    public boolean equals(Object o) {
        return o != null && o.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /** Socket whose output waits {@code delay} ms on every flush (one RMI message). */
    private static final class DelayedSocket extends Socket {
        private final long delay;
        private OutputStream out;

        DelayedSocket(long delay) {
            this.delay = delay;
        }

        @Override
        public synchronized OutputStream getOutputStream() throws IOException {
            if (out == null) {
                out = new FilterOutputStream(super.getOutputStream()) {
                    @Override
                    public void write(byte[] b, int off, int len) throws IOException {
                        out.write(b, off, len);
                    }

                    @Override
                    public void flush() throws IOException {
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        super.flush();
                    }
                };
            }
            return out;
        }
    }
}
