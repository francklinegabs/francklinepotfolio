# 69. Why does MarkRecord have withPublished?

*Topic: My code (WP1)*

`listMarks` returns records whose published flag reflects the unit's current state. `withPublished` returns a new record instead of changing a stored one, so stored data stays immutable.
