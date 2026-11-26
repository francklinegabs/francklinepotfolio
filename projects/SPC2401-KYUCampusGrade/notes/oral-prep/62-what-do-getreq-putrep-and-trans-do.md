# 62. What do getreq, putrep and trans do?

*Topic: Section 5 – Amoeba*

A server thread blocks in getreq for the next request on its port and answers with putrep. A client sends a request and waits for the reply with trans, a blocking RPC.
