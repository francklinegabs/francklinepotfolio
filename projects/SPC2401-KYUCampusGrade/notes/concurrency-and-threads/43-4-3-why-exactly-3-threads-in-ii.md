# 43. 4.3: why exactly 3 threads in (ii)?

*Topic: Section 4 – synchronisation and invocation cost*

While one thread waits 6 ms for the disk, others need 4 ms of CPU each. 10 / 4 = 2.5 threads keep the CPU busy, rounded up to 3.
