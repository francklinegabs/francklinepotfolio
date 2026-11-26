# 47. Why is the key sid + unit a bug?

*Topic: Section 4 – Synchronisation and invocation cost*

String concatenation is ambiguous: "KYU0011" + "SPC" equals "KYU001" + "1SPC". We use a `Key(studentId, unitCode)` record instead (test U03).
