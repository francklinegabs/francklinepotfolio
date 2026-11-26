# 77. What does GradeServer do on shutdown?

*Topic: My code (WP1)*

A shutdown hook unbinds the name, unexports the object (and the registry, if it created one), and closes the pools and the journal, so the port is freed and nothing is half-written.
