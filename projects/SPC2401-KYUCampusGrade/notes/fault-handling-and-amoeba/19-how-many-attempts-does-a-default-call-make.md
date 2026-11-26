# 19. How many attempts does a default call make?

*Topic: RetryingCaller – timeout, retry, back-off*

`client.retries = 3`, so 4 attempts in total, each with a 5 s timeout. Test I03 confirms that with no server the client gives up after 4 attempts.
