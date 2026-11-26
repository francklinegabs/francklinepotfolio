package ke.ac.kyu.campusgrade.api;

import java.io.Serial;

/** A mark or ID was rejected by validation (e.g. mark 150 or a blank student ID). */
public class InvalidMarkException extends GradeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidMarkException(String message) {
        super(message);
    }
}
