# 03. What happens inside put() under the write lock?

*Topic: MarkStore – my thread-safe store*

It stores the new MarkRecord, adds the student to the unit's set, increments the unit version and the submitted counter. All four change together, so no reader sees half an update.
