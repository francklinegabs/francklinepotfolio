package ke.ac.kyu.campusgrade.client;

import ke.ac.kyu.campusgrade.api.GradeException;
import ke.ac.kyu.campusgrade.api.MarkRecord;
import ke.ac.kyu.campusgrade.common.EventLog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

/**
 * Lecturer client. Calls submitMark, publishMarks, getMark and listMarks.
 *
 * <pre>
 * Interactive:  java ... LecturerClient
 * Scripted:     java ... LecturerClient "submit KYU001 SPC2401 78; publish SPC2401; list SPC2401"
 * </pre>
 * Commands: submit &lt;student&gt; &lt;unit&gt; &lt;mark&gt; | publish &lt;unit&gt; |
 * get &lt;student&gt; &lt;unit&gt; | list &lt;unit&gt; | help | quit
 */
public final class LecturerClient implements AutoCloseable {

    private final RetryingCaller rmi;
    private final EventLog log;

    public LecturerClient(RetryingCaller rmi, EventLog log) {
        this.rmi = rmi;
        this.log = log;
    }

    /** Submits a mark. One request ID is created here and reused by every retry. */
    public String submit(String studentId, String unitCode, int mark)
            throws GradeException, RetryingCaller.GaveUpException {
        String requestId = UUID.randomUUID().toString();
        rmi.call("submitMark(" + studentId + "," + unitCode + "," + mark + ") req=" + requestId,
                s -> {
                    s.submitMark(requestId, studentId, unitCode, mark);
                    return null;
                });
        return requestId;
    }

    public void publish(String unitCode) throws GradeException, RetryingCaller.GaveUpException {
        rmi.call("publishMarks(" + unitCode + ")", s -> {
            s.publishMarks(unitCode);
            return null;
        });
    }

    public int get(String studentId, String unitCode) throws GradeException, RetryingCaller.GaveUpException {
        return rmi.call("getMark(" + studentId + "," + unitCode + ")", s -> s.getMark(studentId, unitCode));
    }

    public List<MarkRecord> list(String unitCode) throws GradeException, RetryingCaller.GaveUpException {
        return rmi.call("listMarks(" + unitCode + ")", s -> s.listMarks(unitCode));
    }

    /** Runs one command line; returns false on quit. */
    public boolean run(String line) {
        String[] p = line.trim().split("\\s+");
        if (p.length == 0 || p[0].isEmpty()) {
            return true;
        }
        try {
            switch (p[0].toLowerCase()) {
                case "submit" -> {
                    need(p, 4);
                    String req = submit(p[1], p[2], Integer.parseInt(p[3]));
                    log.info("OK stored " + p[1] + " " + p[2] + " = " + p[3] + " (req " + req + ")");
                }
                case "publish" -> {
                    need(p, 2);
                    publish(p[1]);
                    log.info("OK published " + p[1]);
                }
                case "get" -> {
                    need(p, 3);
                    log.info("MARK " + p[1] + " " + p[2] + " = " + get(p[1], p[2]));
                }
                case "list" -> {
                    need(p, 2);
                    List<MarkRecord> rows = list(p[1]);
                    log.info("LIST " + p[1] + ": " + rows.size() + " mark(s)");
                    rows.forEach(r -> log.info("  " + r));
                }
                case "sleep" -> Thread.sleep(Long.parseLong(p[1]));
                case "quit", "exit" -> {
                    return false;
                }
                default -> System.out.println(
                        "commands: submit <student> <unit> <mark> | publish <unit> | get <student> <unit>"
                                + " | list <unit> | quit");
            }
        } catch (GradeException e) {
            log.warn("REFUSED " + e.getClass().getSimpleName() + ": " + e.getMessage());
        } catch (RetryingCaller.GaveUpException e) {
            log.failure("GAVE UP " + e.getMessage(), e.getCause() == null ? e : e.getCause());
        } catch (NumberFormatException e) {
            log.warn("not a number: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        return true;
    }

    private static void need(String[] p, int n) {
        if (p.length < n) {
            throw new IllegalArgumentException("usage error: '" + p[0] + "' needs " + (n - 1) + " argument(s)");
        }
    }

    @Override
    public void close() {
        rmi.close();
    }

    public static void main(String[] args) throws Exception {
        EventLog log = new EventLog("lecturer");
        try (LecturerClient c = new LecturerClient(new RetryingCaller(log), log)) {
            if (args.length > 0) {
                for (String cmd : String.join(" ", args).split(";")) {
                    if (!c.run(cmd)) {
                        break;
                    }
                }
                return;
            }
            System.out.println("KYUCampusGrade lecturer client. Type 'help'.");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line;
            System.out.print("lecturer> ");
            while ((line = in.readLine()) != null && c.run(line)) {
                System.out.print("lecturer> ");
            }
        }
        System.exit(0);
    }
}
