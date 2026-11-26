# 15. Step 3: what does the communication module do?

*Topic: Section 1 – Remote invocation*

It takes a TCP connection from RMI's pool (created by our socket factory) and sends a JRMP Call message. On the server, an RMI connection thread reads it.
