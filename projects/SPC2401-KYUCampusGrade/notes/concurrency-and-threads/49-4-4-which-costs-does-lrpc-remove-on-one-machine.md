# 49. 4.4: which costs does LRPC remove on one machine?

*Topic: Section 4 – synchronisation and invocation cost*

Network and acknowledgement go entirely; copying drops to one copy onto a shared argument stack; context switches become a direct domain switch on the caller's thread.
