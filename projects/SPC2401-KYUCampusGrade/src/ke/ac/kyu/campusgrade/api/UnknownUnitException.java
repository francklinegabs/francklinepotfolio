package ke.ac.kyu.campusgrade.api;

import java.io.Serial;

/** publishMarks was called for a unit that has no marks. */
public class UnknownUnitException extends GradeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UnknownUnitException(String message) {
        super(message);
    }
}
