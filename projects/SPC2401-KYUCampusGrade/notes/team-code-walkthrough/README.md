# Team code walkthrough – study cards (Franckline Were)

The oral check can ask any member about any part, so these cards walk through my teammates' packages and how I ran the repository.

## MarkStore (concurrency package)

1. [What maps does MarkStore keep?](01-what-maps-does-markstore-keep.md)
2. [Why a read/write lock instead of synchronized?](02-why-a-read-write-lock-instead-of-synchronized.md)
3. [What does put() return and why?](03-what-does-put-return-and-why.md)
4. [How does publish() know a unit is 'already published'?](04-how-does-publish-know-a-unit-is-already-published.md)
5. [Why does studentsIn() return a copy?](05-why-does-studentsin-return-a-copy.md)
6. [Why does list() sort by student ID?](06-why-does-list-sort-by-student-id.md)
7. [Which test proves MarkStore loses no updates?](07-which-test-proves-markstore-loses-no-updates.md)
8. [Does MarkStore ever call a client?](08-does-markstore-ever-call-a-client.md)

## WorkerPool

9. [What does WorkerPool.execute do?](09-what-does-workerpool-execute-do.md)
10. [How are errors from a worker passed back?](10-how-are-errors-from-a-worker-passed-back.md)
11. [What happens when server.workers is 0?](11-what-happens-when-server-workers-is-0.md)
12. [How does the simulated CPU cost work?](12-how-does-the-simulated-cpu-cost-work.md)
13. [How does the simulated disk cost work?](13-how-does-the-simulated-disk-cost-work.md)
14. [What did the pool measurements show?](14-what-did-the-pool-measurements-show.md)

## SubscriptionManager (notification package)

15. [How is a lease granted?](15-how-is-a-lease-granted.md)
16. [Why is expiresAtMillis volatile?](16-why-is-expiresatmillis-volatile.md)
17. [What does renew() do with an expired subscription?](17-what-does-renew-do-with-an-expired-subscription.md)
18. [Why remove(subscription) and not remove(studentId)?](18-why-remove-subscription-and-not-remove-studentid.md)
19. [What does activeFor() return?](19-what-does-activefor-return.md)
20. [Who calls reapExpired()?](20-who-calls-reapexpired.md)
21. [Why use a ConcurrentHashMap here?](21-why-use-a-concurrenthashmap-here.md)

## Notifier

22. [How does the Notifier send callbacks?](22-how-does-the-notifier-send-callbacks.md)
23. [What happens when one callback fails?](23-what-happens-when-one-callback-fails.md)
24. [What is a PublishReport?](24-what-is-a-publishreport.md)
25. [Why does publishMarks return before the callbacks finish?](25-why-does-publishmarks-return-before-the-callbacks.md)
26. [How long did a dead student take to fail in experiment B?](26-how-long-did-a-dead-student-take-to-fail-in-experi.md)
27. [What does the measured 2.2 result show about the Notifier?](27-what-does-the-measured-2-2-result-show-about-the-n.md)
28. [Why are notifier threads daemons?](28-why-are-notifier-threads-daemons.md)

## StudentClient

29. [How does the student client become reachable for callbacks?](29-how-does-the-student-client-become-reachable-for-c.md)
30. [When does the student renew its lease?](30-when-does-the-student-renew-its-lease.md)
31. [What does markPublished() do on the student side?](31-what-does-markpublished-do-on-the-student-side.md)
32. [Why call getMark inside the callback on purpose?](32-why-call-getmark-inside-the-callback-on-purpose.md)
33. [What does --units do?](33-what-does-units-do.md)
34. [What does --exit-after do?](34-what-does-exit-after-do.md)
35. [What happens when the student client closes?](35-what-happens-when-the-student-client-closes.md)
36. [What is client.callback.hostname for?](36-what-is-client-callback-hostname-for.md)

## How the tests are built

37. [What helpers does TestSupport provide?](37-what-helpers-does-testsupport-provide.md)
38. [Why a manual clock?](38-why-a-manual-clock.md)
39. [How does Proc start separate JVMs?](39-how-does-proc-start-separate-jvms.md)
