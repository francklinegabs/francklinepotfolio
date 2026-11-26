# 31. What does markPublished() do on the student side?

*Topic: StudentClient*

It logs 'NOTIFIED marks published for SPC2401', records the unit, then calls getMark inside the callback and logs 'MARK KYU001 SPC2401 = 78'.
