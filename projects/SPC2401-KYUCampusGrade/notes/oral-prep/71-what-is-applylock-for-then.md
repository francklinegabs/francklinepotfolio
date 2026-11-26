# 71. What is applyLock for, then?

*Topic: My code (WP1)*

It keeps the journal order identical to the in-memory order for submits and publishes. The critical section is tiny and never contains a remote call.
