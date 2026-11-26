# 01. Which three defects in the given class does my MarkStore fix?

*Topic: MarkStore – my thread-safe store*

The lost update on the submitted counter, the unsafe concurrent HashMap, and the ambiguous `sid + unit` key. One write lock covers the whole update and the key is a `(studentId, unitCode)` record.
