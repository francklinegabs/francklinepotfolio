package ke.ac.kyu.campusgrade.api;

import java.io.Serial;

/** The student has no live subscription (never subscribed, unsubscribed, or the lease expired). */
public class NotSubscribedException extends GradeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotSubscribedException(String message) {
        super(message);
    }
}
