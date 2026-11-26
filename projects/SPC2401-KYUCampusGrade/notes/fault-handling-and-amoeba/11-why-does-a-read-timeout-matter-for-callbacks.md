# 11. Why does a read timeout matter for callbacks?

*Topic: KyuClientSocketFactory – timeouts and simulated delay*

Without it, a notifier thread calling a student whose laptop vanished mid-call could block forever. With 15 s, the thread is freed and the listener is removed.
