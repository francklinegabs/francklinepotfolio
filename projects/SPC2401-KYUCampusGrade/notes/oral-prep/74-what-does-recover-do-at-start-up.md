# 74. What does recover() do at start-up?

*Topic: My code (WP1)*

It replays every journal line: M lines re-store marks and re-remember request IDs, and P lines re-publish units. A half-written last line from a crash is skipped.
