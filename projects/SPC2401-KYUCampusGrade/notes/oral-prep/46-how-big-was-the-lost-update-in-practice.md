# 46. How big was the lost update in practice?

*Topic: Section 4 – Synchronisation and invocation cost*

With 20 threads x 5,000 submits, the original code lost 34,290 of 100,000 counter updates in the final run (26,975 to 95,443 in other runs). The fixed store lost none (test D03).
