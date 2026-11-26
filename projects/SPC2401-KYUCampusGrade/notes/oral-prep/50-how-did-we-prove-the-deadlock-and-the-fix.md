# 50. How did we prove the deadlock and the fix?

*Topic: Section 4 – Synchronisation and invocation cost*

Test D01 runs the original class with the callback's getMark on a second thread: the publisher stays WAITING and getMark stays BLOCKED. D02 runs the same pattern on our implementation and it fetches 78 in a few milliseconds.
