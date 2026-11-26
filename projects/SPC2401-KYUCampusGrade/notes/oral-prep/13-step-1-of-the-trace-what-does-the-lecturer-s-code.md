# 13. Step 1 of the trace: what does the lecturer's code do?

*Topic: Section 1 – Remote invocation*

It makes a request ID, then calls `stub.submitMark(...)` like a local method through `RetryingCaller`, which starts a 5 s timeout. The thread blocks until a reply or an exception arrives.
