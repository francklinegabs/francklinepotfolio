# 24. A RemoteException with 'Connection reset' during the call: was the mark stored?

*Topic: Section 1 – Remote invocation*

Maybe. The server may have died before storing (experiment A1) or after (A2). The client retries with the same request ID, and the server applies it only if it has not seen that ID.
