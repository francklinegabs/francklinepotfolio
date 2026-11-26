# 08. How is a correction after publication handled?

*Topic: MarkStore – my thread-safe store*

put() bumps the unit version, so the next publish sees a change and notifies again, while a plain repeated publish is ignored (TC13).
