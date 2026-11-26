package ke.ac.kyu.campusgrade.server;

import ke.ac.kyu.campusgrade.api.MarkRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread-safe in-memory marks store.
 *
 * <p>Fixes for the defects in the Section 4 code:
 * <ul>
 *   <li>the key is a (studentId, unitCode) pair, not {@code sid + unit}, so
 *       "KYU0011"+"SPC" and "KYU001"+"1SPC" can no longer collide;</li>
 *   <li>the read-modify-write of the submitted counter happens inside the
 *       write lock, so no update is lost;</li>
 *   <li>reads take the shared read lock, so 2,000 concurrent getMark calls do
 *       not queue behind each other.</li>
 * </ul>
 * No method here ever calls out to a client, so the lock is never held during
 * a remote callback.
 */
public final class MarkStore {

    /** Outcome of a publish request. */
    public enum PublishResult { PUBLISHED, ALREADY_PUBLISHED, UNKNOWN_UNIT }

    private record Key(String studentId, String unitCode) { }

    private final Map<Key, MarkRecord> marks = new HashMap<>();
    private final Map<String, Set<String>> studentsByUnit = new HashMap<>();
    /** Incremented on every change to a unit; lets publish be idempotent. */
    private final Map<String, Long> unitVersion = new HashMap<>();
    private final Map<String, Long> publishedVersion = new HashMap<>();
    private long submitted;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /** Stores or replaces a mark. Returns the previous mark, or -1. */
    public int put(String studentId, String unitCode, int mark, long nowMillis) {
        lock.writeLock().lock();
        try {
            boolean published = publishedVersion.containsKey(unitCode);
            MarkRecord old = marks.put(new Key(studentId, unitCode),
                    new MarkRecord(studentId, unitCode, mark, nowMillis, published));
            studentsByUnit.computeIfAbsent(unitCode, u -> new LinkedHashSet<>()).add(studentId);
            unitVersion.merge(unitCode, 1L, Long::sum);
            submitted++;
            return old == null ? -1 : old.getMark();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public OptionalInt get(String studentId, String unitCode) {
        lock.readLock().lock();
        try {
            MarkRecord r = marks.get(new Key(studentId, unitCode));
            return r == null ? OptionalInt.empty() : OptionalInt.of(r.getMark());
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean isPublished(String unitCode) {
        lock.readLock().lock();
        try {
            return publishedVersion.containsKey(unitCode);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Marks a unit as published. Publishing again with no change since the
     * last publish is a no-op, which makes publishMarks safe to retry.
     */
    public PublishResult publish(String unitCode) {
        lock.writeLock().lock();
        try {
            Long version = unitVersion.get(unitCode);
            if (version == null) {
                return PublishResult.UNKNOWN_UNIT;
            }
            Long prev = publishedVersion.put(unitCode, version);
            return version.equals(prev) ? PublishResult.ALREADY_PUBLISHED : PublishResult.PUBLISHED;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /** Snapshot copy of the students with a mark in a unit. */
    public Set<String> studentsIn(String unitCode) {
        lock.readLock().lock();
        try {
            return new LinkedHashSet<>(studentsByUnit.getOrDefault(unitCode, Set.of()));
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<MarkRecord> list(String unitCode) {
        lock.readLock().lock();
        try {
            boolean published = publishedVersion.containsKey(unitCode);
            List<MarkRecord> out = new ArrayList<>();
            for (String sid : studentsByUnit.getOrDefault(unitCode, Set.of())) {
                out.add(marks.get(new Key(sid, unitCode)).withPublished(published));
            }
            out.sort(Comparator.comparing(MarkRecord::getStudentId));
            return out;
        } finally {
            lock.readLock().unlock();
        }
    }

    /** Total number of submitMark calls that were applied. */
    public long submittedCount() {
        lock.readLock().lock();
        try {
            return submitted;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try {
            return marks.size();
        } finally {
            lock.readLock().unlock();
        }
    }
}
