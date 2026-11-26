# 09. In which JVM does the socket factory run?

*Topic: KyuClientSocketFactory – timeouts and simulated delay*

In the caller's JVM. It is serialised inside the stub, so on a lecturer's PC it uses the lecturer's config, and for callbacks it runs in the server and uses the server's config.
