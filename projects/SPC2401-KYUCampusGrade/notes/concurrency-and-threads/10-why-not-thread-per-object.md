# 10. Why not thread-per-object?

*Topic: WorkerPool – my thread architecture*

There is only one GradeService object, so every request would queue behind one thread: 2,000 x 10 ms = 20 s of waiting for the last student.
