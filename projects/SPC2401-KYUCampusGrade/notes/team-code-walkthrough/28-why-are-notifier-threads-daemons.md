# 28. Why are notifier threads daemons?

*Topic: Notifier*

So a stuck callback can never keep the JVM alive when the server shuts down.
