# 02. Why is the Key a Java record?

*Topic: MarkStore – my thread-safe store*

A record gives `equals` and `hashCode` over both fields automatically, so ("KYU0011", "SPC") and ("KYU001", "1SPC") are different keys, which test U03 checks.
