# 23. A RemoteException with 'Connection refused': was the mark stored?

*Topic: Section 1 – Remote invocation*

No. The request never reached a server, so it is safe to retry. The client also looks the name up again, because the service may have restarted or moved.
