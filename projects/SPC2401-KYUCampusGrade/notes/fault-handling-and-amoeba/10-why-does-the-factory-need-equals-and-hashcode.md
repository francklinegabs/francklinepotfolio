# 10. Why does the factory need equals and hashCode?

*Topic: KyuClientSocketFactory – timeouts and simulated delay*

RMI compares factories to decide whether two stubs can share pooled connections. Making all instances equal lets every call to the same server reuse connections.
