# 13. Why is the CPU simulated with a busy loop and not sleep?

*Topic: WorkerPool – my thread architecture*

Sleeping frees the CPU, but real computation keeps it busy. The loop spins for 4 ms so the CPU really is occupied, which is what 4.3 models.
