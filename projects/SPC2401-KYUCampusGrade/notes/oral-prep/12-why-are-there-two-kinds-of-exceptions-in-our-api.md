# 12. Why are there two kinds of exceptions in our API?

*Topic: RMI in our design*

`RemoteException` means the network failed and the call may or may not have run, so clients retry it. `GradeException` subclasses such as `InvalidMarkException` mean the server ran the call and said no; retrying would give the same answer, so clients never retry them.
