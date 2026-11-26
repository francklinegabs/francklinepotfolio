# 45. 4.3 (iii): why are user-level threads no better than one thread here?

*Topic: Section 4 – synchronisation and invocation cost*

The kernel sees one thread. When it blocks on the disk, the whole process blocks, so no other user thread runs: 100 requests per second.
