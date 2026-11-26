# 32. Which layer converts a mark into bytes, and which finds GradeService by name?

*Topic: Section 2 – Notification and middleware*

Both are middleware. Marshalling is Java serialisation inside the RMI stub and dispatcher; naming is the RMI registry (`rebind` and `lookup`).
