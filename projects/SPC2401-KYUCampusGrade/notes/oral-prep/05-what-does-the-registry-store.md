# 05. What does the registry store?

*Topic: RMI in our design*

A map from a name to a stub. `GradeServer` calls `rebind("GradeService", stub)`; clients call `lookup("GradeService")` and receive a copy of that stub. The registry itself is just another remote object, usually on port 1099.
