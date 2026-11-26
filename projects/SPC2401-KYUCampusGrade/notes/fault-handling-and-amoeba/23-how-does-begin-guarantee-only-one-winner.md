# 23. How does begin() guarantee only one winner?

*Topic: RequestDeduplicator – at-most-once*

It calls `putIfAbsent` on a ConcurrentHashMap, which is atomic. Exactly one caller inserts the entry and gets `first = true`; all others get the existing entry.
