# 26. How long are request IDs remembered?

*Topic: RequestDeduplicator – at-most-once*

24 hours (`dedup.window.ms`). Housekeeping purges completed IDs older than that, which bounds memory.
