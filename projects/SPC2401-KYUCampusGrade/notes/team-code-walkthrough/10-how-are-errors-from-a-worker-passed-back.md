# 10. How are errors from a worker passed back?

*Topic: WorkerPool*

A GradeException or RuntimeException is re-thrown as itself; anything else is wrapped in RemoteException. An interrupt becomes a RemoteException too.
