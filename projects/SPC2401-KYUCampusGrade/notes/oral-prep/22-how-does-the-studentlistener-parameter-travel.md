# 22. How does the StudentListener parameter travel?

*Topic: Section 1 – Remote invocation*

By remote reference. Because it extends `Remote` and is exported, RMI sends a stub (the student's IP, port and object ID) instead of the object. The server then calls back into the student's JVM.
