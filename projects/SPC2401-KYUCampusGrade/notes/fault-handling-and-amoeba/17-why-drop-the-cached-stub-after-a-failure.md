# 17. Why drop the cached stub after a failure?

*Topic: RetryingCaller – timeout, retry, back-off*

The server may have restarted or moved, so the old stub points to a dead endpoint. Setting `cached = null` makes the next attempt look the name up in the registry again.
