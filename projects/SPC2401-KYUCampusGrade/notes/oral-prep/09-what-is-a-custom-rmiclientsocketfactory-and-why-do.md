# 09. What is a custom RMIClientSocketFactory and why do we use one?

*Topic: RMI in our design*

It decides how client sockets are created. `KyuClientSocketFactory` adds connect and read timeouts so a dead peer cannot block a thread forever, and it can add an artificial delay for the network experiments. It is serialised inside the stub, so it runs in the caller's JVM.
