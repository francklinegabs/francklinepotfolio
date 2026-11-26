# 32. Why are journal appends synchronized?

*Topic: Journal – surviving a crash*

Two threads writing at once could interleave their lines. The service also writes the journal and the store under one small lock, so journal order matches memory order.
