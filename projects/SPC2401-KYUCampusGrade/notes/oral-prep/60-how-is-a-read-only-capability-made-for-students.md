# 60. How is a read-only capability made for students?

*Topic: Section 5 – Amoeba*

The lecturer sends the owner capability and the rights mask READ to the server. The server verifies it, then returns a capability with rights = READ and check = f(C XOR READ), where C is the object's secret random number and f is one-way.
