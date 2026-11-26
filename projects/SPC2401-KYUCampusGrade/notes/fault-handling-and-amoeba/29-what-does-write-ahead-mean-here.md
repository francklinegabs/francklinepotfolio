# 29. What does write-ahead mean here?

*Topic: Journal – surviving a crash*

The line is written and flushed before memory changes and before the reply is sent. If the server dies after replying, the mark is already on disk.
