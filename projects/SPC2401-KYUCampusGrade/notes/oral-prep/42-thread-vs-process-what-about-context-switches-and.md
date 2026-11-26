# 42. Thread vs process: what about context switches and crashes?

*Topic: Section 3 – OS types, processes, threads*

Threads in one process share page tables, so switching between them is cheaper. A process is isolated: if it crashes, the server keeps working, whereas a fatal error inside a thread can take down the whole JVM.
