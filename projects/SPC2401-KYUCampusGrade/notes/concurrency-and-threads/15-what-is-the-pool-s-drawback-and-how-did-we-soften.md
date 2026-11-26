# 15. What is the pool's drawback, and how did we soften it?

*Topic: WorkerPool – my thread architecture*

In a burst, requests queue and latency rises, and a request blocked on I/O holds a worker. Callbacks run on a separate notifier pool, and clients time out and retry.
