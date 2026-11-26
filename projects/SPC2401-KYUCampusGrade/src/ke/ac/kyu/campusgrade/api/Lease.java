package ke.ac.kyu.campusgrade.api;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/** A subscription lease. Passed by value (serialised) from server to client. */
public final class Lease implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String studentId;
    private final long durationMillis;
    private final long expiresAtMillis;

    public Lease(String studentId, long durationMillis, long expiresAtMillis) {
        this.studentId = studentId;
        this.durationMillis = durationMillis;
        this.expiresAtMillis = expiresAtMillis;
    }

    public String getStudentId() { return studentId; }
    public long getDurationMillis() { return durationMillis; }
    public long getExpiresAtMillis() { return expiresAtMillis; }

    @Override
    public String toString() {
        return "Lease[" + studentId + ", " + durationMillis + " ms, expires "
                + Instant.ofEpochMilli(expiresAtMillis) + "]";
    }
}
