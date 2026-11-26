# 54. Why do more threads than in (ii) not help?

*Topic: Section 4 – Synchronisation and invocation cost*

At 100% CPU, extra threads only queue for the CPU and add context switches and memory. Our benchmark on one simulated core flattened at 246 requests/s from 4 workers upward.
