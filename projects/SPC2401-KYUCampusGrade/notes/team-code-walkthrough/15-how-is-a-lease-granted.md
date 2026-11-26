# 15. How is a lease granted?

*Topic: SubscriptionManager (notification package)*

A requested lease of 0 or less gets the default (10 min); otherwise the server grants the smaller of the request and the maximum (1 hour). TC16 checks the cap.
