# 40. Cause 1 in 1.4: server down. Mark stored? Client action?

*Topic: Invocation semantics and failures*

Not stored; the connection was refused before anything was sent. Retry after back-off and look the name up again.
