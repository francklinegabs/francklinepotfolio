# 36. Why did we still choose a thread for the notifier?

*Topic: Section 3 – OS types, processes and threads*

It needs the live subscriber list on every publish and runs often; shared memory and cheap threads win, and the risks are contained in code.
