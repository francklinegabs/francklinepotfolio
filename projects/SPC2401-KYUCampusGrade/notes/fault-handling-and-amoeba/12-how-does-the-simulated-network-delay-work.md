# 12. How does the simulated network delay work?

*Topic: KyuClientSocketFactory – timeouts and simulated delay*

When `net.delay.ms` > 0, the factory returns a socket whose output stream sleeps that long on every flush, which is once per RMI message. The delay is added before the bytes leave the JVM.
