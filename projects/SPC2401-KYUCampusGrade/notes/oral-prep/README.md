# Oral-check study cards – KYUCampusGrade

One question per card, answered from this project's own design and measured results.

## RMI in our design

1. [What is a remote object in KYUCampusGrade?](01-what-is-a-remote-object-in-kyucampusgrade.md)
2. [Why does GradeService extend java.rmi.Remote?](02-why-does-gradeservice-extend-java-rmi-remote.md)
3. [Why does every remote method declare RemoteException?](03-why-does-every-remote-method-declare-remoteexcepti.md)
4. [What is the stub in Java RMI today?](04-what-is-the-stub-in-java-rmi-today.md)
5. [What does the registry store?](05-what-does-the-registry-store.md)
6. [Why rebind instead of bind?](06-why-rebind-instead-of-bind.md)
7. [What is the difference between rmiregistry and createRegistry?](07-what-is-the-difference-between-rmiregistry-and-cre.md)
8. [What is java.rmi.server.hostname for?](08-what-is-java-rmi-server-hostname-for.md)
9. [What is a custom RMIClientSocketFactory and why do we use one?](09-what-is-a-custom-rmiclientsocketfactory-and-why-do.md)
10. [What is distributed garbage collection in RMI?](10-what-is-distributed-garbage-collection-in-rmi.md)
11. [Which JVM thread runs a remote method?](11-which-jvm-thread-runs-a-remote-method.md)
12. [Why are there two kinds of exceptions in our API?](12-why-are-there-two-kinds-of-exceptions-in-our-api.md)

## Section 1 – Remote invocation

13. [Step 1 of the trace: what does the lecturer's code do?](13-step-1-of-the-trace-what-does-the-lecturer-s-code.md)
14. [Step 2: what does the client stub do?](14-step-2-what-does-the-client-stub-do.md)
15. [Step 3: what does the communication module do?](15-step-3-what-does-the-communication-module-do.md)
16. [Step 4: what does the dispatcher (skeleton) do?](16-step-4-what-does-the-dispatcher-skeleton-do.md)
17. [Steps 5 and 6: what happens in the implementation and on the reply path?](17-steps-5-and-6-what-happens-in-the-implementation-a.md)
18. [Why is submitMark at-most-once?](18-why-is-submitmark-at-most-once.md)
19. [Why is getMark at-least-once?](19-why-is-getmark-at-least-once.md)
20. [How did we make publishMarks safe to retry?](20-how-did-we-make-publishmarks-safe-to-retry.md)
21. [How does a String parameter travel?](21-how-does-a-string-parameter-travel.md)
22. [How does the StudentListener parameter travel?](22-how-does-the-studentlistener-parameter-travel.md)
23. [A RemoteException with 'Connection refused': was the mark stored?](23-a-remoteexception-with-connection-refused-was-the.md)
24. [A RemoteException with 'Connection reset' during the call: was the mark stored?](24-a-remoteexception-with-connection-reset-during-the.md)

## Section 2 – Notification and middleware

25. [What are our three notification event types?](25-what-are-our-three-notification-event-types.md)
26. [Why push the event but pull the mark?](26-why-push-the-event-but-pull-the-mark.md)
