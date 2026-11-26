# 16. Which errors are never retried, and why?

*Topic: RetryingCaller – timeout, retry, back-off*

Any `GradeException`, e.g. InvalidMarkException for mark 150. The server received the call and said no, so retrying would give the same answer.
