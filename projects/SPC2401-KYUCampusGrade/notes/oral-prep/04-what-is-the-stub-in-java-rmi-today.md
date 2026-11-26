# 04. What is the stub in Java RMI today?

*Topic: RMI in our design*

Since Java 5 the stub is a dynamic proxy generated at run time; `rmic` is no longer needed. It implements `GradeService`, and each method marshals its arguments and sends them through a `UnicastRef` to the server's endpoint.
