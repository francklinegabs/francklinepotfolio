# 29. How does the student client become reachable for callbacks?

*Topic: StudentClient*

It exports itself with `UnicastRemoteObject.exportObject` and our socket factory, then passes its own stub to `subscribe`.
