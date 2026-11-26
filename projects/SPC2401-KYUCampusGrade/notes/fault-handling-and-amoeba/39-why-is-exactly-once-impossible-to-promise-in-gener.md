# 39. Why is exactly-once impossible to promise in general?

*Topic: Invocation semantics and failures*

If the network stays down, the client can never learn the outcome. We can only guarantee at most once on the server and report 'unknown' to the user, as in experiment C.
