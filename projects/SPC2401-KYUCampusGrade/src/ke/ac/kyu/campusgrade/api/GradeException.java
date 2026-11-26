package ke.ac.kyu.campusgrade.api;

import java.io.Serial;

/**
 * Base class for application errors. These are checked and are NOT retried
 * by clients: the call reached the server and the server said no.
 * RemoteException, in contrast, means the call may not have reached it.
 */
public class GradeException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public GradeException(String message) {
        super(message);
    }
}
