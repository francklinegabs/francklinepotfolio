package ke.ac.kyu.campusgrade.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface of the KYUCampusGrade service.
 *
 * <p>The first five methods are exactly the ones given in the assignment scenario.
 * The extra methods add what the scenario needs to run safely over a network:
 * a request ID for at-most-once submitMark, a lease on subscriptions, and a
 * lecturer view of a unit's marks.
 */
public interface GradeService extends Remote {

    /** Name the service is bound under in the RMI registry. */
    String SERVICE_NAME = "GradeService";

    // ---- Scenario methods ------------------------------------------------

    /** Stores a mark (0..100). Without a request ID a retry may apply twice. */
    void submitMark(String studentId, String unitCode, int mark)
            throws RemoteException, InvalidMarkException;

    /** Returns a published mark for one student and unit. */
    int getMark(String studentId, String unitCode)
            throws RemoteException, UnknownStudentException, MarkNotPublishedException;

    /** Publishes a unit and notifies subscribed students who have a mark in it. */
    void publishMarks(String unitCode) throws RemoteException, UnknownUnitException;

    /** Subscribes a student with the server's default lease. */
    void subscribe(String studentId, StudentListener listener)
            throws RemoteException, InvalidMarkException;

    /** Removes a student's subscription. Unknown students are ignored (idempotent). */
    void unsubscribe(String studentId) throws RemoteException;

    // ---- Extensions needed for fault tolerance ---------------------------

    /**
     * submitMark with a client-generated request ID. A retry that reuses the
     * same ID is recognised by the server and applied at most once.
     */
    void submitMark(String requestId, String studentId, String unitCode, int mark)
            throws RemoteException, InvalidMarkException;

    /** Subscribes with a requested lease; the server may grant a shorter one. */
    Lease subscribe(String studentId, StudentListener listener, long requestedLeaseMillis)
            throws RemoteException, InvalidMarkException;

    /** Extends a lease. Throws if the lease already expired and was removed. */
    Lease renewLease(String studentId) throws RemoteException, NotSubscribedException;

    /** Lecturer view of every mark in a unit, published or not. */
    List<MarkRecord> listMarks(String unitCode) throws RemoteException;
}
