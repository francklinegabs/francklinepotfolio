package ke.ac.kyu.campusgrade.server;

import ke.ac.kyu.campusgrade.api.InvalidMarkException;

import java.util.regex.Pattern;

/** Input checks done before any work, so bad requests never touch the store. */
final class Validation {

    static final int MIN_MARK = 0;
    static final int MAX_MARK = 100;

    private static final Pattern ID = Pattern.compile("[A-Za-z0-9][A-Za-z0-9_./-]{0,31}");
    private static final Pattern REQUEST_ID = Pattern.compile("[A-Za-z0-9-]{1,64}");

    private Validation() {
    }

    static void studentId(String s) throws InvalidMarkException {
        if (s == null || !ID.matcher(s).matches()) {
            throw new InvalidMarkException("Invalid student ID '" + s + "'");
        }
    }

    static void unitCode(String u) throws InvalidMarkException {
        if (u == null || !ID.matcher(u).matches()) {
            throw new InvalidMarkException("Invalid unit code '" + u + "'");
        }
    }

    static void mark(int m) throws InvalidMarkException {
        if (m < MIN_MARK || m > MAX_MARK) {
            throw new InvalidMarkException("Mark " + m + " is outside " + MIN_MARK + ".." + MAX_MARK);
        }
    }

    static void requestId(String r) throws InvalidMarkException {
        if (r != null && !REQUEST_ID.matcher(r).matches()) {
            throw new InvalidMarkException("Invalid request ID '" + r + "'");
        }
    }

    /** Same rule as unitCode but for methods that cannot throw InvalidMarkException. */
    static boolean isValidId(String s) {
        return s != null && ID.matcher(s).matches();
    }
}
