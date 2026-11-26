# 02. Why a read/write lock instead of synchronized?

*Topic: MarkStore (concurrency package)*

Most calls after publishing are getMark reads. A ReentrantReadWriteLock lets many readers run together, while a write (submit or publish) gets exclusive access.
