# 56. What does LRPC remove for a call on the same machine?

*Topic: Section 4 – Synchronisation and invocation cost*

The network time and the acknowledgement go entirely. Copying shrinks to one copy onto a shared argument stack, and context switches become a direct domain switch on the client's own thread.
