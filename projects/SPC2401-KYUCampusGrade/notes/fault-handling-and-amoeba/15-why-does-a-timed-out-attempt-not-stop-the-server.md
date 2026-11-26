# 15. Why does a timed-out attempt not stop the server?

*Topic: RetryingCaller – timeout, retry, back-off*

Cancelling only abandons the local wait; the request may still be in flight or running on the server. That is why every retry of submitMark reuses the same request ID.
