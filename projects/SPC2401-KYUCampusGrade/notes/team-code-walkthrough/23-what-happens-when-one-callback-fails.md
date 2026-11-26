# 23. What happens when one callback fails?

*Topic: Notifier*

The RemoteException or RuntimeException is caught, the subscription is removed, a FAILURE line is logged, and the other callbacks carry on.
