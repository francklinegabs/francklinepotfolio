# 49. Step by step: how is a read-only capability made for students?

*Topic: Section 5 – Amoeba*

The lecturer sends the owner capability and rights mask READ to the server. The server checks it against C, builds rights = READ and check = f(C XOR READ), and returns the new capability.
