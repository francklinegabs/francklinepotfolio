# 19. Why did D01 originally fail with IllegalThreadStateException?

*Topic: My Section 4 reproduction tests*

It called `setDaemon` after the thread had started, which Java forbids. Setting daemon before `start()` fixed it.
