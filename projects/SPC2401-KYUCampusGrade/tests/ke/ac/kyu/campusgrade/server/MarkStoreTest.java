package ke.ac.kyu.campusgrade.server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Unit tests for the marks store (no RMI). */
class MarkStoreTest {

    @Test
    @DisplayName("U01 put then get returns the mark; replace returns the old one")
    void putGetReplace() {
        MarkStore s = new MarkStore();
        assertEquals(-1, s.put("KYU001", "SPC2401", 78, 1));
        assertEquals(78, s.get("KYU001", "SPC2401").getAsInt());
        assertEquals(78, s.put("KYU001", "SPC2401", 81, 2));
        assertEquals(81, s.get("KYU001", "SPC2401").getAsInt());
        assertEquals(1, s.size());
        assertEquals(2, s.submittedCount());
    }

    @Test
    @DisplayName("U02 unknown student or unit gives empty")
    void unknown() {
        MarkStore s = new MarkStore();
        s.put("KYU001", "SPC2401", 78, 1);
        assertTrue(s.get("KYU999", "SPC2401").isEmpty());
        assertTrue(s.get("KYU001", "SPC9999").isEmpty());
    }

    @Test
    @DisplayName("U03 composite key: 'KYU0011'+'SPC' never collides with 'KYU001'+'1SPC'")
    void noKeyCollision() {
        MarkStore s = new MarkStore();
        s.put("KYU0011", "SPC", 40, 1);
        s.put("KYU001", "1SPC", 90, 1);
        assertEquals(40, s.get("KYU0011", "SPC").getAsInt());
        assertEquals(90, s.get("KYU001", "1SPC").getAsInt());
    }

    @Test
    @DisplayName("U04 publish is idempotent until the unit changes")
    void publishVersioning() {
        MarkStore s = new MarkStore();
        assertEquals(MarkStore.PublishResult.UNKNOWN_UNIT, s.publish("SPC2401"));
        s.put("KYU001", "SPC2401", 78, 1);
        assertFalse(s.isPublished("SPC2401"));
        assertEquals(MarkStore.PublishResult.PUBLISHED, s.publish("SPC2401"));
        assertEquals(MarkStore.PublishResult.ALREADY_PUBLISHED, s.publish("SPC2401"));
        s.put("KYU001", "SPC2401", 80, 2);               // correction after publication
        assertEquals(MarkStore.PublishResult.PUBLISHED, s.publish("SPC2401"));
    }

    @Test
    @DisplayName("U05 50 threads x 1,000 puts: no lost update to the counter or the map")
    void concurrentPutsLoseNothing() throws Exception {
        MarkStore s = new MarkStore();
        int threads = 50;
        int each = 1_000;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch go = new CountDownLatch(1);
        List<Future<?>> fs = new ArrayList<>();
        for (int t = 0; t < threads; t++) {
            int id = t;
            fs.add(pool.submit(() -> {
                go.await();
                for (int i = 0; i < each; i++) {
                    s.put("S" + id + "-" + i, "SPC2401", i % 101, i);
                }
                return null;
            }));
        }
        go.countDown();
        for (Future<?> f : fs) {
            f.get();
        }
        pool.shutdown();
        assertEquals(threads * each, s.submittedCount());
        assertEquals(threads * each, s.size());
        assertEquals(threads * each, s.studentsIn("SPC2401").size());
    }
}
