# 27. Why only purge completed entries?

*Topic: RequestDeduplicator – at-most-once*

An entry still in progress has callers that may be waiting on it. Removing it early would let a duplicate run a second time.
