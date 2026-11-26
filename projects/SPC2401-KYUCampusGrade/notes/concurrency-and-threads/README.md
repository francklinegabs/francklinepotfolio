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
22. [Why does D03 not assert a specific loss for the original?](22-why-does-d03-not-assert-a-specific-loss-for-the-or.md)
23. [What helpers did I add in TestSupport?](23-what-helpers-did-i-add-in-testsupport.md)
24. [What does MarkStoreTest U05 check?](24-what-does-markstoretest-u05-check.md)

## Section 3 – OS types, processes and threads

25. [Rate a network OS for 'hides where GradeService runs'.](25-rate-a-network-os-for-hides-where-gradeservice-run.md)
26. [Rate a distributed OS for 'each campus server stays autonomous'.](26-rate-a-distributed-os-for-each-campus-server-stays.md)
27. [Rate a multiprocessor OS for 'survives one node failing'.](27-rate-a-multiprocessor-os-for-survives-one-node-fai.md)
28. [Rate each OS type for 'scales to 20,000 students'.](28-rate-each-os-type-for-scales-to-20-000-students.md)
29. [Which OS type did we choose for three servers, in one sentence?](29-which-os-type-did-we-choose-for-three-servers-in-o.md)
30. [Which transparency is violated when clients switch to a backup by hand?](30-which-transparency-is-violated-when-clients-switch.md)
31. [Which transparency is violated by different methods for cached and server marks?](31-which-transparency-is-violated-by-different-method.md)
32. [Thread vs process: address space?](32-thread-vs-process-address-space.md)
33. [Thread vs process: what did creation really cost?](33-thread-vs-process-what-did-creation-really-cost.md)
34. [Why is a thread switch cheaper than a process switch?](34-why-is-a-thread-switch-cheaper-than-a-process-swit.md)
35. [What is the crash risk of a notifier thread, and how do we limit it?](35-what-is-the-crash-risk-of-a-notifier-thread-and-ho.md)
36. [Why did we still choose a thread for the notifier?](36-why-did-we-still-choose-a-thread-for-the-notifier.md)
37. [How does Java map threads onto the OS?](37-how-does-java-map-threads-onto-the-os.md)

## Section 4 – synchronisation and invocation cost

38. [Draw the lost-update interleaving in words.](38-draw-the-lost-update-interleaving-in-words.md)
39. [Why does making only getMark synchronized not protect the map?](39-why-does-making-only-getmark-synchronized-not-prot.md)
40. [State the four conditions for deadlock using 4.2.](40-state-the-four-conditions-for-deadlock-using-4-2.md)
41. [Why does RMI make this deadlock permanent?](41-why-does-rmi-make-this-deadlock-permanent.md)
42. [Give two other valid fixes for 4.2 besides ours.](42-give-two-other-valid-fixes-for-4-2-besides-ours.md)
43. [4.3: why exactly 3 threads in (ii)?](43-4-3-why-exactly-3-threads-in-ii.md)
44. [4.3: what if the disk can serve only one request at a time?](44-4-3-what-if-the-disk-can-serve-only-one-request-at.md)
45. [4.3 (iii): why are user-level threads no better than one thread here?](45-4-3-iii-why-are-user-level-threads-no-better-than.md)
