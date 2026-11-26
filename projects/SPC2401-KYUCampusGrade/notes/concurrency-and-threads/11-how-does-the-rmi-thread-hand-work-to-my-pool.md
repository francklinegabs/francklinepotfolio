# 11. How does the RMI thread hand work to my pool?

*Topic: WorkerPool – my thread architecture*

It submits a task and blocks on `Future.get()`. The RMI thread only waits; the worker does the job, and the pool size caps how many jobs run at once.
