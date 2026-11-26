# Concurrency, threads and synchronisation – study cards (Eunice Gikaru)

One question per card, answered from my own package (WP3) and report Sections 3 and 4, with numbers we measured.

## MarkStore – my thread-safe store

1. [Which three defects in the given class does my MarkStore fix?](01-which-three-defects-in-the-given-class-does-my-mar.md)
2. [Why is the Key a Java record?](02-why-is-the-key-a-java-record.md)
3. [What happens inside put() under the write lock?](03-what-happens-inside-put-under-the-write-lock.md)
4. [Why can getMark readers run at the same time?](04-why-can-getmark-readers-run-at-the-same-time.md)
5. [Why do I unlock in a finally block every time?](05-why-do-i-unlock-in-a-finally-block-every-time.md)
6. [What does publish() return for an unknown unit, and what does the service do with it?](06-what-does-publish-return-for-an-unknown-unit-and-w.md)
7. [Why keep a separate submitted counter when the map has a size?](07-why-keep-a-separate-submitted-counter-when-the-map.md)
8. [How is a correction after publication handled?](08-how-is-a-correction-after-publication-handled.md)

## WorkerPool – my thread architecture

9. [Why did I choose a worker pool over thread-per-request?](09-why-did-i-choose-a-worker-pool-over-thread-per-req.md)
10. [Why not thread-per-object?](10-why-not-thread-per-object.md)
11. [How does the RMI thread hand work to my pool?](11-how-does-the-rmi-thread-hand-work-to-my-pool.md)
12. [How are worker threads named and why?](12-how-are-worker-threads-named-and-why.md)
13. [Why is the CPU simulated with a busy loop and not sleep?](13-why-is-the-cpu-simulated-with-a-busy-loop-and-not.md)
14. [Why is the simulated-core semaphore fair?](14-why-is-the-simulated-core-semaphore-fair.md)
15. [What is the pool's drawback, and how did we soften it?](15-what-is-the-pool-s-drawback-and-how-did-we-soften.md)
16. [How would you size the pool from 4.3's numbers?](16-how-would-you-size-the-pool-from-4-3-s-numbers.md)

## My Section 4 reproduction tests

17. [How does D01 make the original code deadlock in a test?](17-how-does-d01-make-the-original-code-deadlock-in-a.md)
18. [How does D01 detect the deadlock without hanging the test run?](18-how-does-d01-detect-the-deadlock-without-hanging-t.md)
19. [Why did D01 originally fail with IllegalThreadStateException?](19-why-did-d01-originally-fail-with-illegalthreadstat.md)
20. [What does D02 show?](20-what-does-d02-show.md)
21. [What does D03 compare?](21-what-does-d03-compare.md)
