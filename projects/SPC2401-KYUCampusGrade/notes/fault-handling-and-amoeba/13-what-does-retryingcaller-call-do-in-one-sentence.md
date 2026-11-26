# 13. What does RetryingCaller.call do, in one sentence?

*Topic: RetryingCaller – timeout, retry, back-off*

It runs one remote operation with a per-attempt timeout and, on a network failure or timeout, logs it, drops the cached stub, waits with back-off and tries again, up to `retries + 1` attempts.
