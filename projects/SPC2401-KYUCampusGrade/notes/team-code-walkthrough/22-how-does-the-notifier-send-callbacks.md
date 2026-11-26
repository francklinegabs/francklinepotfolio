# 22. How does the Notifier send callbacks?

*Topic: Notifier*

For each subscriber it runs `markPublished(unit)` as a CompletableFuture on a fixed pool of 20 daemon threads named notifier-1, notifier-2 and so on.
