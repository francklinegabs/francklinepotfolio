# 17. Steps 5 and 6: what happens in the implementation and on the reply path?

*Topic: Section 1 – Remote invocation*

The implementation checks 0..100, journals the mark and stores it under the write lock. The dispatcher then sends a JRMP Return: a normal-return byte for `void`, or a serialised exception that the stub re-throws in the lecturer's thread.
