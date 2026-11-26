package ke.ac.kyu.campusgrade.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

/**
 * Configuration lookup. Nothing about hosts or ports is hard-coded in the
 * program logic; every value is resolved in this order:
 * <ol>
 *   <li>JVM system property  {@code -Dkyu.registry.host=10.0.0.5}</li>
 *   <li>environment variable {@code KYU_REGISTRY_HOST=10.0.0.5}</li>
 *   <li>the file named by {@code kyu.config} (default {@code config/kyu.properties})</li>
 *   <li>the default passed by the caller</li>
 * </ol>
 */
public final class Config {

    public static final String REGISTRY_HOST = "registry.host";
    public static final String REGISTRY_PORT = "registry.port";
    public static final String REGISTRY_CREATE = "registry.create";
    public static final String SERVER_HOSTNAME = "server.hostname";
    public static final String SERVER_PORT = "server.port";
    public static final String SERVER_WORKERS = "server.workers";
    public static final String NOTIFIER_THREADS = "notifier.threads";
    public static final String LEASE_DEFAULT_MS = "lease.default.ms";
    public static final String LEASE_MAX_MS = "lease.max.ms";
    public static final String DEDUP_WINDOW_MS = "dedup.window.ms";
    public static final String JOURNAL_FILE = "server.journal";
    public static final String CLIENT_TIMEOUT_MS = "client.timeout.ms";
    public static final String CLIENT_RETRIES = "client.retries";
    public static final String CLIENT_BACKOFF_MS = "client.backoff.ms";
    public static final String CALLBACK_HOSTNAME = "client.callback.hostname";
    public static final String SOCKET_TIMEOUT_MS = "socket.timeout.ms";
    public static final String NET_DELAY_MS = "net.delay.ms";
    public static final String SIM_CPU_MS = "sim.cpu.ms";
    public static final String SIM_DISK_MS = "sim.disk.ms";
    public static final String SIM_CORES = "sim.cores";
    public static final String SIM_POST_APPLY_MS = "sim.postapply.ms";
    public static final String LOG_DIR = "log.dir";

    private static final String PREFIX = "kyu.";
    private static volatile Properties fileProps;

    private Config() {
    }

    public static String get(String key, String defaultValue) {
        String v = System.getProperty(PREFIX + key);
        if (v == null || v.isBlank()) {
            v = System.getenv(envName(key));
        }
        if (v == null || v.isBlank()) {
            v = file().getProperty(key);
        }
        return (v == null || v.isBlank()) ? defaultValue : v.trim();
    }

    public static int getInt(String key, int defaultValue) {
        String v = get(key, null);
        try {
            return v == null ? defaultValue : Integer.parseInt(v);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Config " + key + " must be an integer, got '" + v + "'");
        }
    }

    public static long getLong(String key, long defaultValue) {
        String v = get(key, null);
        try {
            return v == null ? defaultValue : Long.parseLong(v);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Config " + key + " must be a number, got '" + v + "'");
        }
    }

    public static boolean getBool(String key, boolean defaultValue) {
        String v = get(key, null);
        return v == null ? defaultValue : Boolean.parseBoolean(v);
    }

    /** Registry host: must come from configuration (default file says localhost). */
    public static String registryHost() {
        return get(REGISTRY_HOST, "localhost");
    }

    public static int registryPort() {
        return getInt(REGISTRY_PORT, java.rmi.registry.Registry.REGISTRY_PORT);
    }

    static String envName(String key) {
        return "KYU_" + key.toUpperCase(Locale.ROOT).replace('.', '_');
    }

    /** Clears cached file values; used by tests. */
    public static void reload() {
        fileProps = null;
    }

    private static Properties file() {
        Properties p = fileProps;
        if (p == null) {
            p = new Properties();
            String name = System.getProperty("kyu.config", "config/kyu.properties");
            Path path = Path.of(name);
            if (Files.isRegularFile(path)) {
                try (InputStream in = Files.newInputStream(path)) {
                    p.load(in);
                } catch (IOException e) {
                    System.err.println("Could not read " + path + ": " + e.getMessage());
                }
            }
            fileProps = p;
        }
        return p;
    }
}
