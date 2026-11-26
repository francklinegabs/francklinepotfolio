# 75. What does housekeep() do and how often?

*Topic: My code (WP1)*

It reaps expired leases and purges request IDs older than the window (24 h). It runs on a daemon scheduler every quarter of the lease time, capped between 50 ms and 5 s.
