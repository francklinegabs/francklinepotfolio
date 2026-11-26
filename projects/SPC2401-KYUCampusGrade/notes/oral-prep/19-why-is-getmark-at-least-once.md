# 19. Why is getMark at-least-once?

*Topic: Section 1 – Remote invocation*

It is a pure read. Running it twice returns the same mark and changes nothing, so the server needs to remember nothing and the client may simply retry.
