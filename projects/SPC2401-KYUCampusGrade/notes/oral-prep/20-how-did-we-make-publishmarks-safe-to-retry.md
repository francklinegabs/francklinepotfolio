# 20. How did we make publishMarks safe to retry?

*Topic: Section 1 – Remote invocation*

The store keeps a version number per unit. A publish with no change since the last one is reported as ALREADY_PUBLISHED and sends no notifications, so a retried publish does not spam 2,000 students (test TC13).
