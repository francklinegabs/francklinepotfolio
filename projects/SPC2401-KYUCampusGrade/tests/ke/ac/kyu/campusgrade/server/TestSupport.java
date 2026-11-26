package ke.ac.kyu.campusgrade.server;

import ke.ac.kyu.campusgrade.api.StudentListener;
import ke.ac.kyu.campusgrade.common.EventLog;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/** Shared helpers for the server tests. */
final class TestSupport {

    private TestSupport() {
    }

    /** A clock the test moves by hand, for lease tests. */
    static final class ManualClock {
        final AtomicLong now = new AtomicLong(1_000_000L);

        long get() {
            return now.get();
        }

        void advance(long ms) {
            now.addAndGet(ms);
        }
    }

    static ServerSettings settings() {
        ServerSettings s = new ServerSettings();
        s.workers = 4;
        s.notifierThreads = 4;
        s.defaultLeaseMillis = 60_000;
        s.maxLeaseMillis = 120_000;
        s.journalFile = null;
        return s;
    }

    static GradeServiceImpl service() throws IOException {
        return new GradeServiceImpl(settings(), EventLog.silent("test"));
    }

    /** In-process listener that records the units it was told about. */
    static final class RecordingListener implements StudentListener {
        final List<String> units = new CopyOnWriteArrayList<>();

        @Override
        public void markPublished(String unitCode) {
            units.add(unitCode);
        }
    }

    /** Listener whose client has gone away, as RMI would report it. */
    static final class DeadListener implements StudentListener {
        @Override
        public void markPublished(String unitCode) throws RemoteException {
            throw new ConnectException("Connection refused to host: 10.0.0.99");
        }
    }
}
