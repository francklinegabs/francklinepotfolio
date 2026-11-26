# 39. Why does making only getMark synchronized not protect the map?

*Topic: Section 4 – synchronisation and invocation cost*

submitMark does not take the same lock, so a put can run during a get or another put. Mutual exclusion only works if every access uses the same lock.
