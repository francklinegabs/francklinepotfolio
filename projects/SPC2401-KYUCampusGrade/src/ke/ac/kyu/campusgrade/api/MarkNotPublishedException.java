package ke.ac.kyu.campusgrade.api;

import java.io.Serial;

/** The mark exists but the unit has not been published yet. */
public class MarkNotPublishedException extends GradeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public MarkNotPublishedException(String message) {
        super(message);
    }
}
