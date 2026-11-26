# 05. Why do I unlock in a finally block every time?

*Topic: MarkStore – my thread-safe store*

If anything threw while the lock was held and the unlock were skipped, every later caller would block forever. `finally` guarantees the lock is released.
