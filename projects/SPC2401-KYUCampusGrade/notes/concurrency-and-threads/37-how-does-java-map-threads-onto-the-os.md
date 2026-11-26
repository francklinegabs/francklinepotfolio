# 37. How does Java map threads onto the OS?

*Topic: Section 3 – OS types, processes and threads*

Platform threads are 1:1 kernel threads, so the OS scheduler runs our RMI, worker and notifier threads directly. That is why a blocking call only blocks one thread, unlike 4.3 (iii).
