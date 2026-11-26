# 39. Why a worker pool for 2,000 students in 10 minutes?

*Topic: Section 3 – OS types, processes, threads*

The burst is 2,000 x 10 ms = 20 s of work. A fixed pool bounds threads and memory and queues the burst; thread-per-request would create up to 2,000 threads, and thread-per-object would push everything through one thread.
