# 67. What does Validation check, and why before any work?

*Topic: My code (WP1)*

Marks 0..100, IDs matching `[A-Za-z0-9][A-Za-z0-9_./-]{0,31}`, and request IDs of letters, digits and dashes. Rejecting bad input first means it never touches the store or the journal, and the journal format needs no escaping.
