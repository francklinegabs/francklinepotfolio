# 18. Why is submitMark at-most-once?

*Topic: Section 1 – Remote invocation*

It is not idempotent: each run adds to the submitted count, and a late retry of 78 could overwrite a newer correction to 80. So the server remembers request IDs it has already applied and ignores repeats (test TC07).
