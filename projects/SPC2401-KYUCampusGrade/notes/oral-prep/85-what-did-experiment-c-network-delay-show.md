# 85. What did experiment C (network delay) show?

*Topic: Testing and evidence*

With 300 ms per message the call just got slower. With 1,500 ms and a 2.5 s timeout, all three attempts timed out and the client gave up, yet the server applied the mark once and ignored the late duplicates.
