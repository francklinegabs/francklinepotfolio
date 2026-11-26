# 25. Why does publishMarks return before the callbacks finish?

*Topic: Notifier*

The lecturer should not wait for 2,000 students. The service keeps the future in `lastPublication` so tests can still wait for it.
