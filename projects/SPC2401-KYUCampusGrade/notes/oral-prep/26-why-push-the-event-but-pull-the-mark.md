# 26. Why push the event but pull the mark?

*Topic: Section 2 – Notification and middleware*

The callback only says "SPC2401 is published". Each student then calls `getMark` for their own mark, so marks never travel to the wrong person, and the server does not need to know which data each client wants.
