# 17. What does renew() do with an expired subscription?

*Topic: SubscriptionManager (notification package)*

It removes it and throws NotSubscribedException, so the client knows to subscribe again instead of thinking it is still covered.
