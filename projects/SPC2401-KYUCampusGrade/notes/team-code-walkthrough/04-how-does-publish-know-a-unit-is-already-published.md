# 04. How does publish() know a unit is 'already published'?

*Topic: MarkStore (concurrency package)*

It compares the unit's current version with the version stored at the last publish. Equal means nothing changed, so no notification is needed.
