# 86. Why was 1 worker 88 requests/s and not 100?

*Topic: Testing and evidence*

On Windows, `Thread.sleep(6)` really takes about 6.72 ms, and RMI adds about 0.4 ms: 1,000 / (4 + 6.72 + 0.4) is about 90 requests/s.
