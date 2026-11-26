# 34. Why is a thread switch cheaper than a process switch?

*Topic: Section 3 – OS types, processes and threads*

Threads of one process share page tables, so the TLB and caches need not be refilled; a process switch changes the address space.
