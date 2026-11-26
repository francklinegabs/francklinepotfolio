# 08. What does KyuClientSocketFactory do on every new socket?

*Topic: KyuClientSocketFactory – timeouts and simulated delay*

It connects with a timeout of `socket.timeout.ms`, sets the same value as the read timeout (SO_TIMEOUT) and turns on TCP_NODELAY.
