package ke.ac.kyu.campusgrade.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Callback interface implemented by student clients. The student exports an
 * object of this type and passes it to subscribe(); it travels by remote
 * reference, so the server calls back into the student's JVM.
 */
public interface StudentListener extends Remote {
    void markPublished(String unitCode) throws RemoteException;
}
