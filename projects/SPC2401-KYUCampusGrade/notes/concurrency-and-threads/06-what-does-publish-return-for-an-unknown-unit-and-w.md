# 06. What does publish() return for an unknown unit, and what does the service do with it?

*Topic: MarkStore – my thread-safe store*

`UNKNOWN_UNIT`. The service turns it into UnknownUnitException, so the lecturer learns that no marks exist for that unit (test TC09).
