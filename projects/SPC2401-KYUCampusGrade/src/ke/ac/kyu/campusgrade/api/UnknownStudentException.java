package ke.ac.kyu.campusgrade.api;

import java.io.Serial;

/** No mark is stored for this student in this unit. */
public class UnknownStudentException extends GradeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UnknownStudentException(String message) {
        super(message);
    }
}
