# 49. Which of the four deadlock conditions does our fix break?

*Topic: Section 4 – Synchronisation and invocation cost*

Hold-and-wait: publishMarks no longer holds any lock while it waits for a remote call. It takes a snapshot of the subscribers, releases the lock, and the notifier pool makes the callbacks.
