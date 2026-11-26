# 48. Explain the deadlock in 4.2 in one breath.

*Topic: Section 4 – Synchronisation and invocation cost*

T1 runs the synchronised publishMarks, holds the monitor and waits for the student's callback. The callback calls getMark, which arrives on T2 and blocks on that same monitor. T1 waits for T2 and T2 waits for T1, which is a circular wait.
