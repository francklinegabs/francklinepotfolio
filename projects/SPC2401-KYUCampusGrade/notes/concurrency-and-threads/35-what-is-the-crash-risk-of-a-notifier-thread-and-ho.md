# 35. What is the crash risk of a notifier thread, and how do we limit it?

*Topic: Section 3 – OS types, processes and threads*

A fatal JVM error in it would stop the whole server. We catch every callback failure, use socket timeouts and bound the pool, so one bad student cannot hurt the server.
