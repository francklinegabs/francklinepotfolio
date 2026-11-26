# 04. Why can getMark readers run at the same time?

*Topic: MarkStore – my thread-safe store*

They take the read lock, which many threads can hold together. Only a writer needs exclusive access, and writes are rare compared with the 2,000 reads after publishing.
