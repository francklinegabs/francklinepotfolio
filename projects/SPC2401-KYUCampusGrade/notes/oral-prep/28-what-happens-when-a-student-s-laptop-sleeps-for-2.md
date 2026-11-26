# 28. What happens when a student's laptop sleeps for 2 days?

*Topic: Section 2 – Notification and middleware*

Renewals stop, so within 10 minutes the lease expires and the housekeeping thread removes the subscription. On waking, `renewLease` throws `NotSubscribedException`; the client re-subscribes and pulls `getMark` for its units to catch missed publications (TC14, TC15).
