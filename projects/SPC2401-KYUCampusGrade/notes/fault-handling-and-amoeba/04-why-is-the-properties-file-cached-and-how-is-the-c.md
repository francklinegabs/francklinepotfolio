# 04. Why is the properties file cached, and how is the cache cleared?

*Topic: Config – no hard-coded host or port*

Reading a file on every lookup would be wasteful, so it is loaded once into a `volatile` field. `Config.reload()` clears it, which tests use.
