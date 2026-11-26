# 43. Why did we run the notifier as threads inside the server?

*Topic: Section 3 – OS types, processes, threads*

It needs the live subscriber list on every publish, so shared memory and cheap threads win. We reduce the crash risk by catching every callback failure, using socket timeouts and bounding the pool.
