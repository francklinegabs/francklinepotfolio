# 76. How does getMark decide which exception to throw?

*Topic: My code (WP1)*

No stored mark gives `UnknownStudentException`; a stored mark in an unpublished unit gives `MarkNotPublishedException`. Otherwise it returns the mark. Invalid IDs are treated as unknown so they never reach the store.
