# 30. Does flush() survive every kind of crash?

*Topic: Journal – surviving a crash*

It survives a killed JVM, because the data is already in the OS buffers. It would not survive a power cut, which needs `fsync` (FileChannel.force); we list that as a limitation.
