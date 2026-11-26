# 41. Why does RMI make this deadlock permanent?

*Topic: Section 4 – synchronisation and invocation cost*

The callback is a blocking remote call and RMI sets no response timeout by default, so the publisher waits forever.
