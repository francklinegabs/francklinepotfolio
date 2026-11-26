# 22. What problem does RequestDeduplicator solve?

*Topic: RequestDeduplicator – at-most-once*

A retried submitMark could apply twice, or a late retry could overwrite a newer correction. Remembering request IDs makes each ID apply at most once (test TC07).
