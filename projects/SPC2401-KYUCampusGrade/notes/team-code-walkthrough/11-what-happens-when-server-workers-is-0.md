# 11. What happens when server.workers is 0?

*Topic: WorkerPool*

No pool is created and the task runs directly on the RMI thread. The benchmark uses this to measure pure invocation cost.
