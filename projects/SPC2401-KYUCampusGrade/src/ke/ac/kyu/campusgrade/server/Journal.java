package ke.ac.kyu.campusgrade.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Append-only write-ahead journal. Each applied submitMark and publishMarks is
 * written here before it changes memory, and replayed on start-up. Because the
 * request ID is journaled with the mark, the at-most-once guarantee survives
 * a server crash and restart (shown in experiment 7.3-A2).
 *
 * <pre>
 * M|requestId|studentId|unitCode|mark|epochMillis
 * P|unitCode|epochMillis
 * </pre>
 * IDs are validated to contain no '|', so the format needs no escaping.
 */
public final class Journal implements AutoCloseable {

    /** Callback used during replay. */
    public interface Replayer {
        void mark(String requestId, String studentId, String unitCode, int mark, long when);

        void publish(String unitCode, long when);
    }

    private final Path path;
    private final BufferedWriter out;

    private Journal(Path path, BufferedWriter out) {
        this.path = path;
        this.out = out;
    }

    /** Opens (creating if needed) the journal. A null or blank name disables journaling. */
    public static Journal open(String file) throws IOException {
        if (file == null || file.isBlank() || file.equalsIgnoreCase("none")) {
            return new Journal(null, null);
        }
        Path p = Path.of(file);
        if (p.getParent() != null) {
            Files.createDirectories(p.getParent());
        }
        BufferedWriter w = Files.newBufferedWriter(p, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        return new Journal(p, w);
    }

    public boolean enabled() {
        return out != null;
    }

    /** Replays every entry. Returns the number of entries read; a torn last line is ignored. */
    public int replay(Replayer r) throws IOException {
        if (path == null || !Files.exists(path)) {
            return 0;
        }
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        int n = 0;
        for (String line : lines) {
            String[] f = line.split("\\|");
            try {
                if (f[0].equals("M") && f.length == 6) {
                    String req = f[1].equals("-") ? null : f[1];
                    r.mark(req, f[2], f[3], Integer.parseInt(f[4]), Long.parseLong(f[5]));
                    n++;
                } else if (f[0].equals("P") && f.length == 3) {
                    r.publish(f[1], Long.parseLong(f[2]));
                    n++;
                }
            } catch (NumberFormatException ignored) {
                // half-written line from a crash: skip it
            }
        }
        return n;
    }

    public synchronized void appendMark(String requestId, String studentId, String unitCode,
                                        int mark, long when) throws IOException {
        if (out == null) {
            return;
        }
        out.write("M|" + (requestId == null ? "-" : requestId) + "|" + studentId + "|" + unitCode
                + "|" + mark + "|" + when);
        out.newLine();
        out.flush();   // reaches the OS before we reply; survives a killed JVM
    }

    public synchronized void appendPublish(String unitCode, long when) throws IOException {
        if (out == null) {
            return;
        }
        out.write("P|" + unitCode + "|" + when);
        out.newLine();
        out.flush();
    }

    @Override
    public synchronized void close() throws IOException {
        if (out != null) {
            out.close();
        }
    }
}
