# 40. What is the worker pool's drawback and its mitigation?

*Topic: Section 3 – OS types, processes, threads*

In a burst, requests queue and latency rises, and a request blocked on I/O holds a worker. Mitigation: size the pool from the CPU-to-wait ratio, keep callbacks on a separate notifier pool, and use client timeouts.
