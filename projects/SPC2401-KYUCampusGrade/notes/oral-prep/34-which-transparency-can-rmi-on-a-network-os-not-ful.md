# 34. Which transparency can RMI on a network OS not fully give?

*Topic: Section 2 – Notification and middleware*

Failure transparency. The three Linux servers are independent kernels, so if one crashes mid-call the client sees a `RemoteException` and must retry or fail over itself, as our `RetryingCaller` does.
