# 32. Thread vs process: address space?

*Topic: Section 3 – OS types, processes and threads*

Threads share the server's heap, so the notifier reads the subscription map directly. A separate process would need IPC or RMI for every publish.
