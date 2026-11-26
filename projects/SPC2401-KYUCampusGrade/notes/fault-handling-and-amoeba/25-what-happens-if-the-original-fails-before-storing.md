# 25. What happens if the original fails before storing?

*Topic: RequestDeduplicator – at-most-once*

`abort()` removes the ID and completes the future exceptionally. A waiting duplicate sees that, loops, and claims the ID itself, so the retry can still succeed.
