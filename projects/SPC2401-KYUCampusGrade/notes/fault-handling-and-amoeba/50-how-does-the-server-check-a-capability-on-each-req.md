# 50. How does the server check a capability on each request?

*Topic: Section 5 – Amoeba*

It looks up C for the object, recomputes f(C XOR rights) from the rights field, and compares it with the check field. A mismatch means the capability was forged or altered.
