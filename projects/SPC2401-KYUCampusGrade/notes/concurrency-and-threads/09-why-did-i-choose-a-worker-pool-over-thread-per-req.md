# 09. Why did I choose a worker pool over thread-per-request?

*Topic: WorkerPool – my thread architecture*

The burst after publishing is 2,000 requests in a few minutes. Thread-per-request could create 2,000 threads with about 1 MB of stack each; a fixed pool of 8 keeps threads and memory bounded.
