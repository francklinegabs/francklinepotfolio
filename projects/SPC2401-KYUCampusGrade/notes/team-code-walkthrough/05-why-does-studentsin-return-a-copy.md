# 05. Why does studentsIn() return a copy?

*Topic: MarkStore (concurrency package)*

The notifier uses the list after the lock is released. A copy cannot change underneath it, so no lock is needed while sending callbacks.
