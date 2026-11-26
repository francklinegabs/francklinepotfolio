# 72. Walk through applySubmit with a request ID.

*Topic: My code (WP1)*

Claim the ID in the deduplicator. If another call already owns it, wait for that call's result and return. Otherwise journal the mark, store it, mark the ID complete and log it. If storing fails, release the ID so a retry can run.
