# 16. How would you size the pool from 4.3's numbers?

*Topic: WorkerPool – my thread architecture*

Threads per core = (CPU + wait) / CPU = (4 + 6) / 4 = 2.5, so 3 per core. On 4 cores that is about 12; we kept 8 as a safe default and measured.
