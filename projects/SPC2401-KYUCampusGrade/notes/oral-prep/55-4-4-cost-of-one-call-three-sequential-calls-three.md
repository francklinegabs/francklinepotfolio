# 55. 4.4: cost of one call, three sequential calls, three concurrent calls?

*Topic: Section 4 – Synchronisation and invocation cost*

0.2 + 0.3 + 0.4 + 1.0 + 0.1 = 2.0 ms per call; 3 x 2.0 = 6.0 ms sequential; about 2.0 ms concurrent. Measured with injected delay: 18.45 ms vs 6.27 ms, a 2.94x speed-up.
