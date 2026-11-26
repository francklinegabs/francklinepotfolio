# 17. How does D01 make the original code deadlock in a test?

*Topic: My Section 4 reproduction tests*

The listener's markPublished starts a second thread that calls getMark (as RMI would) and waits for it. publishMarks holds the monitor, so getMark blocks forever.
