# 35. Where is the request ID created?

*Topic: LecturerClient*

In `LecturerClient.submit`: one `UUID.randomUUID()` per submit, captured by the lambda and reused by every retry in RetryingCaller.
