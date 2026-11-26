# 03. Why does every remote method declare RemoteException?

*Topic: RMI in our design*

Any remote call can fail for network reasons the caller cannot prevent: refused connection, reset, timeout. Declaring the checked `RemoteException` forces client code to handle these failures instead of pretending the call is local.
