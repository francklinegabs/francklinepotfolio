# 38. Why is at-most-once harder than at-least-once?

*Topic: Invocation semantics and failures*

At-least-once only needs retries. At-most-once also needs the server to remember what it has done (the request-ID table) and to keep that memory across crashes (the journal).
