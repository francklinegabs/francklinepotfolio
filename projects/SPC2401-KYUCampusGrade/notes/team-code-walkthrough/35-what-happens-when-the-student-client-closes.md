# 35. What happens when the student client closes?

*Topic: StudentClient*

It stops renewing, calls unsubscribe (if that fails, the lease simply expires on the server) and unexports its listener.
