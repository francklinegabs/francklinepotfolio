# 11. Which JVM thread runs a remote method?

*Topic: RMI in our design*

An "RMI TCP Connection" thread in the server JVM, one per incoming connection. In our server that thread hands the work to a fixed worker pool and waits, which caps how many requests run at once.
