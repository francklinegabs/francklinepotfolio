# 22. Why does D03 not assert a specific loss for the original?

*Topic: My Section 4 reproduction tests*

The number of lost updates depends on timing; it ranged from 26,975 to 95,443 in our runs. The test asserts only the fixed store's exact result and prints the original's loss as evidence.
