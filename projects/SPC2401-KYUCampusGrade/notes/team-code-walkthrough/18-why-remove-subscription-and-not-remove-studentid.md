# 18. Why remove(subscription) and not remove(studentId)?

*Topic: SubscriptionManager (notification package)*

It uses `remove(key, value)`, so a failed callback removes only that old subscription, not a newer one the student created after re-subscribing.
