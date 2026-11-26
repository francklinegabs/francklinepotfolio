# 20. What does classify() report?

*Topic: RetryingCaller – timeout, retry, back-off*

Whether the call can have run: NotBoundException or ConnectException mean 'not executed'; UnmarshalException or ConnectIOException mean 'connection lost during call: may have executed'; anything else is 'may have executed'.
