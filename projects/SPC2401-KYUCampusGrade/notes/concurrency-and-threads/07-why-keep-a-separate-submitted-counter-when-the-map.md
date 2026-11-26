# 07. Why keep a separate submitted counter when the map has a size?

*Topic: MarkStore – my thread-safe store*

Size counts distinct (student, unit) pairs; corrections replace a mark without growing it. The counter counts every applied submit, which is what at-most-once tests check (TC07, I05).
