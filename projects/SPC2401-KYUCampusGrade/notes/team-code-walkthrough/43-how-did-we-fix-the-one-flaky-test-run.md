# 43. How did we fix the one flaky test run?

*Topic: How the tests are built*

Tests that waited fixed times (2 s, 3 s, a short retry budget) now wait for the condition itself with generous limits. Five cold rebuilds then all passed 31/31.
