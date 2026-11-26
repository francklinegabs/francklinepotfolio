# 18. What back-off do we use?

*Topic: RetryingCaller – timeout, retry, back-off*

Exponential: `backoff x 2^(attempt-1)`. With 250 ms that is 250, 500 and 1,000 ms between the four attempts, which avoids hammering a server that is restarting.
