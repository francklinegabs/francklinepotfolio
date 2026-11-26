# 73. Why journal before updating memory (write-ahead)?

*Topic: My code (WP1)*

If the server is killed right after replying, the mark and its request ID must already be on disk, so the restarted server replays them and recognises a retry as a duplicate (experiment A2).
