# 06. Why rebind instead of bind?

*Topic: RMI in our design*

`bind` throws `AlreadyBoundException` if the name exists, so restarting the server after a crash would fail. `rebind` replaces the old, now dead stub, which is what we want when the server comes back.
