# 14. How is the per-attempt timeout implemented?

*Topic: RetryingCaller – timeout, retry, back-off*

Each attempt runs on a daemon thread from a cached pool, and the caller waits with `Future.get(timeoutMillis)`. On timeout it cancels the future and moves on.
