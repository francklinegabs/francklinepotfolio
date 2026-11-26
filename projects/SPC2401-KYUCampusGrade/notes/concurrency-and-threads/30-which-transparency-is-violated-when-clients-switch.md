# 30. Which transparency is violated when clients switch to a backup by hand?

*Topic: Section 3 – OS types, processes and threads*

Failure transparency. The fix is fail-over inside the middleware: the proxy retries and re-looks up a replica list automatically.
