# 45. What is wrong with lines (1)-(3) of the given submitMark?

*Topic: Section 4 – Synchronisation and invocation cost*

`old = submitted; put; submitted = old + 1` is an unsynchronised read-modify-write. Two threads can both read 5 and both write 6, so one update is lost.
