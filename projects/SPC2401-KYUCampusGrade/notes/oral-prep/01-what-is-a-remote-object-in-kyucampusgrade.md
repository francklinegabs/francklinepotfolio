# 01. What is a remote object in KYUCampusGrade?

*Topic: RMI in our design*

`GradeServiceImpl` is the one remote object. `GradeServer` exports it with `UnicastRemoteObject.exportObject`, so RMI listens on a port and can dispatch calls to it. Clients never hold the object, only a stub that forwards calls over TCP.
