# 21. Why use a ConcurrentHashMap here?

*Topic: SubscriptionManager (notification package)*

Subscribe, renew, unsubscribe, publish and housekeeping all run on different threads; the concurrent map makes each operation safe without one big lock.
