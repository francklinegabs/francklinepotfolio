# 10. What is distributed garbage collection in RMI?

*Topic: RMI in our design*

The server keeps a remote object alive while clients hold leases ("dirty" calls) on it. If a client stops renewing, for example because it crashed, the lease expires and the object can be collected. Our subscription leases apply the same idea at the application level.
