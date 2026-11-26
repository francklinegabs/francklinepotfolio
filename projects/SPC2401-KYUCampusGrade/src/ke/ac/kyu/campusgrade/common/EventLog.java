package ke.ac.kyu.campusgrade.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Small timestamped logger. Every line goes to stdout and to
 * {@code <log.dir>/<component>.log}. FAILURE lines form the failure log
 * required by 6.4.
 *
 * <pre>2026-11-26T09:14:03.512+03:00 [FAILURE] [lecturer] submitMark attempt 1/4 failed: ...</pre>
 */
public final class EventLog {

    public enum Level { INFO, WARN, FAILURE }

    private static final DateTimeFormatter TS =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private final String component;
    private final PrintWriter file;
    private final boolean console;

    public EventLog(String component) {
        this(component, Config.get(Config.LOG_DIR, "logs"), true);
    }

    public EventLog(String component, String dir, boolean console) {
        this.component = component;
        this.console = console;
        PrintWriter w = null;
        if (dir != null && !dir.isBlank()) {
            try {
                Path d = Path.of(dir);
                Files.createDirectories(d);
                w = new PrintWriter(Files.newBufferedWriter(d.resolve(component + ".log"),
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND), true);
            } catch (IOException e) {
                System.err.println("Log file disabled: " + e.getMessage());
            }
        }
        this.file = w;
    }

    /** A logger that writes nowhere; used by unit tests. */
    public static EventLog silent(String component) {
        return new EventLog(component, null, false);
    }

    public void info(String msg) { write(Level.INFO, msg); }

    public void warn(String msg) { write(Level.WARN, msg); }

    public void failure(String msg) { write(Level.FAILURE, msg); }

    public void failure(String msg, Throwable t) {
        write(Level.FAILURE, msg + ": " + describe(t));
    }

    /** One-line description of an exception and its root cause. */
    public static String describe(Throwable t) {
        Throwable root = t;
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }
        String s = t.getClass().getSimpleName();
        if (root != t) {
            s += " caused by " + root.getClass().getSimpleName()
                    + (root.getMessage() == null ? "" : " (" + root.getMessage() + ")");
        } else if (t.getMessage() != null) {
            s += " (" + firstLine(t.getMessage()) + ")";
        }
        return s;
    }

    private static String firstLine(String s) {
        int i = s.indexOf('\n');
        return i < 0 ? s : s.substring(0, i);
    }

    private synchronized void write(Level level, String msg) {
        String line = OffsetDateTime.now().format(TS) + " [" + level + "] [" + component + "] "
                + "[" + Thread.currentThread().getName() + "] " + msg;
        if (console) {
            System.out.println(line);
        }
        if (file != null) {
            file.println(line);
        }
    }
}
