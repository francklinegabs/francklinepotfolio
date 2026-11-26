# 61. Why can a student not turn READ into READ+WRITE?

*Topic: Section 5 – Amoeba*

The matching check would be f(C XOR (READ|WRITE)), which needs C. C never leaves the server and f cannot be inverted, so a forged capability fails the server's comparison.
