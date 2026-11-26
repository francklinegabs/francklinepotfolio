# 12. How does the simulated CPU cost work?

*Topic: WorkerPool*

A busy loop with `Thread.onSpinWait()` until 4 ms have passed. With `sim.cores=1`, a fair semaphore lets only one worker spin at a time, imitating one core.
