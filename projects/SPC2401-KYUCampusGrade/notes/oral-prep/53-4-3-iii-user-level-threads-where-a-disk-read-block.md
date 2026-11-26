# 53. 4.3 (iii): user-level threads where a disk read blocks the process?

*Topic: Section 4 – Synchronisation and invocation cost*

The kernel sees one thread, so every disk wait stops all of them. It is back to 10 ms per request: 100 requests per second.
