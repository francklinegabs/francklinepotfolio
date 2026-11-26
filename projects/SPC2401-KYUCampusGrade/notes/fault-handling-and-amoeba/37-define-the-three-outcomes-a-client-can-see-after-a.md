# 37. Define the three outcomes a client can see after a remote call.

*Topic: Invocation semantics and failures*

Success (it ran), an application error (it ran and said no), or a RemoteException/timeout (unknown: it may or may not have run). Fault handling is about the third case.
