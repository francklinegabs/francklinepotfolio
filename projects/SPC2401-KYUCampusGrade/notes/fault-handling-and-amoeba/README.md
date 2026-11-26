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
