# 16. Why is expiresAtMillis volatile?

*Topic: SubscriptionManager (notification package)*

Renewal writes it on an RMI thread while the notifier and housekeeping threads read it; `volatile` makes the new value visible to them at once.
