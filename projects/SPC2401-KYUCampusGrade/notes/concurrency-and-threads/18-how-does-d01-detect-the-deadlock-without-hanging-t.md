# 18. How does D01 detect the deadlock without hanging the test run?

*Topic: My Section 4 reproduction tests*

It waits until the getMark thread's state is BLOCKED, gives the publisher two more seconds, and checks it is still stuck. Both threads are daemons, so the JVM can still exit.
