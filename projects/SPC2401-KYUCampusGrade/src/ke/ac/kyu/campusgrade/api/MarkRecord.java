package ke.ac.kyu.campusgrade.api;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/** One stored mark. Immutable and passed by value (serialised). */
public final class MarkRecord implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String studentId;
    private final String unitCode;
    private final int mark;
    private final long updatedAtMillis;
    private final boolean published;

    public MarkRecord(String studentId, String unitCode, int mark,
                      long updatedAtMillis, boolean published) {
        this.studentId = Objects.requireNonNull(studentId);
        this.unitCode = Objects.requireNonNull(unitCode);
        this.mark = mark;
        this.updatedAtMillis = updatedAtMillis;
        this.published = published;
    }

    public String getStudentId() { return studentId; }
    public String getUnitCode() { return unitCode; }
    public int getMark() { return mark; }
    public long getUpdatedAtMillis() { return updatedAtMillis; }
    public boolean isPublished() { return published; }

    public MarkRecord withPublished(boolean p) {
        return new MarkRecord(studentId, unitCode, mark, updatedAtMillis, p);
    }

    @Override
    public String toString() {
        return studentId + " " + unitCode + " = " + mark
                + (published ? " (published)" : " (draft)")
                + " @ " + Instant.ofEpochMilli(updatedAtMillis);
    }
}
