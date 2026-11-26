# 09. What does WorkerPool.execute do?

*Topic: WorkerPool*

It submits the task to a fixed pool of `server.workers` threads (8 by default) and waits for the result on the RMI thread.
