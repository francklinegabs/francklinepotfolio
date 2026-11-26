# 24. What does a duplicate do while the original is still running?

*Topic: RequestDeduplicator – at-most-once*

It waits on the original's CompletableFuture (up to 30 s) instead of returning early, so the client is never told 'stored' before the mark really is stored.
