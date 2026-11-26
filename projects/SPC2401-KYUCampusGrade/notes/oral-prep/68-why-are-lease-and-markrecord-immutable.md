# 68. Why are Lease and MarkRecord immutable?

*Topic: My code (WP1)*

They are passed by value. Once serialised, the client holds a copy, so mutating it could never update the server anyway. Immutability makes that obvious and makes them safe to share between threads.
