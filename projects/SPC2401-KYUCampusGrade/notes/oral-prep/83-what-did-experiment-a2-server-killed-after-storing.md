# 83. What did experiment A2 (server killed after storing) show?

*Topic: Testing and evidence*

The restarted server recovered the request ID from the journal and logged 'duplicate ... ignored' for the retry. The journal holds one entry: at-most-once survived a crash.
