# 08. Does MarkStore ever call a client?

*Topic: MarkStore (concurrency package)*

Never. It only touches memory, so its lock can never be held during a remote callback, which keeps the 4.2 deadlock impossible.
