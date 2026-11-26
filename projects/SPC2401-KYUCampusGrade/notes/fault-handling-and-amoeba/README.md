# Fault handling and Amoeba – study cards (Ashley Odiwuor)

One question per card, answered from my own package (WP4) and report Section 5, with numbers from our experiments.

## Config – no hard-coded host or port

1. [In what order does Config look up a value?](01-in-what-order-does-config-look-up-a-value.md)
2. [How is a key turned into an environment variable name?](02-how-is-a-key-turned-into-an-environment-variable-n.md)
3. [Which properties file does Config read, and can it be changed?](03-which-properties-file-does-config-read-and-can-it.md)
4. [Why is the properties file cached, and how is the cache cleared?](04-why-is-the-properties-file-cached-and-how-is-the-c.md)

## EventLog – the timestamped failure log

5. [What does one EventLog line look like?](05-what-does-one-eventlog-line-look-like.md)
6. [Where does EventLog write?](06-where-does-eventlog-write.md)
7. [Why is the write method synchronized?](07-why-is-the-write-method-synchronized.md)

## KyuClientSocketFactory – timeouts and simulated delay

8. [What does KyuClientSocketFactory do on every new socket?](08-what-does-kyuclientsocketfactory-do-on-every-new-s.md)
9. [In which JVM does the socket factory run?](09-in-which-jvm-does-the-socket-factory-run.md)
10. [Why does the factory need equals and hashCode?](10-why-does-the-factory-need-equals-and-hashcode.md)
11. [Why does a read timeout matter for callbacks?](11-why-does-a-read-timeout-matter-for-callbacks.md)
12. [How does the simulated network delay work?](12-how-does-the-simulated-network-delay-work.md)

## RetryingCaller – timeout, retry, back-off

13. [What does RetryingCaller.call do, in one sentence?](13-what-does-retryingcaller-call-do-in-one-sentence.md)
14. [How is the per-attempt timeout implemented?](14-how-is-the-per-attempt-timeout-implemented.md)
15. [Why does a timed-out attempt not stop the server?](15-why-does-a-timed-out-attempt-not-stop-the-server.md)
16. [Which errors are never retried, and why?](16-which-errors-are-never-retried-and-why.md)
17. [Why drop the cached stub after a failure?](17-why-drop-the-cached-stub-after-a-failure.md)
18. [What back-off do we use?](18-what-back-off-do-we-use.md)
19. [How many attempts does a default call make?](19-how-many-attempts-does-a-default-call-make.md)
20. [What does classify() report?](20-what-does-classify-report.md)
21. [What is GaveUpException?](21-what-is-gaveupexception.md)

## RequestDeduplicator – at-most-once

22. [What problem does RequestDeduplicator solve?](22-what-problem-does-requestdeduplicator-solve.md)
23. [How does begin() guarantee only one winner?](23-how-does-begin-guarantee-only-one-winner.md)
24. [What does a duplicate do while the original is still running?](24-what-does-a-duplicate-do-while-the-original-is-sti.md)
25. [What happens if the original fails before storing?](25-what-happens-if-the-original-fails-before-storing.md)
26. [How long are request IDs remembered?](26-how-long-are-request-ids-remembered.md)
27. [Why only purge completed entries?](27-why-only-purge-completed-entries.md)

## Journal – surviving a crash

28. [What is the journal's format?](28-what-is-the-journal-s-format.md)
29. [What does write-ahead mean here?](29-what-does-write-ahead-mean-here.md)
30. [Does flush() survive every kind of crash?](30-does-flush-survive-every-kind-of-crash.md)
31. [How does replay handle a half-written last line?](31-how-does-replay-handle-a-half-written-last-line.md)
32. [Why are journal appends synchronized?](32-why-are-journal-appends-synchronized.md)
33. [How is journaling switched off?](33-how-is-journaling-switched-off.md)

## LecturerClient

34. [What commands does the lecturer client accept?](34-what-commands-does-the-lecturer-client-accept.md)
35. [Where is the request ID created?](35-where-is-the-request-id-created.md)
36. [How does the lecturer see an invalid mark?](36-how-does-the-lecturer-see-an-invalid-mark.md)

## Invocation semantics and failures

37. [Define the three outcomes a client can see after a remote call.](37-define-the-three-outcomes-a-client-can-see-after-a.md)
38. [Why is at-most-once harder than at-least-once?](38-why-is-at-most-once-harder-than-at-least-once.md)
39. [Why is exactly-once impossible to promise in general?](39-why-is-exactly-once-impossible-to-promise-in-gener.md)
40. [Cause 1 in 1.4: server down. Mark stored? Client action?](40-cause-1-in-1-4-server-down-mark-stored-client-acti.md)
41. [Cause 2 in 1.4: connection lost mid-call. Mark stored? Client action?](41-cause-2-in-1-4-connection-lost-mid-call-mark-store.md)

## Section 5 – Amoeba

42. [What kind of system is Amoeba?](42-what-kind-of-system-is-amoeba.md)
43. [What are Amoeba's main kinds of machine?](43-what-are-amoeba-s-main-kinds-of-machine.md)
44. [What is the processor pool and how would KYU use it?](44-what-is-the-processor-pool-and-how-would-kyu-use-i.md)
45. [What replaces the RMI registry in Amoeba?](45-what-replaces-the-rmi-registry-in-amoeba.md)
46. [What is a capability?](46-what-is-a-capability.md)
