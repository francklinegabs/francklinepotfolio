# 42. Give two other valid fixes for 4.2 besides ours.

*Topic: Section 4 – synchronisation and invocation cost*

The student could fetch the mark after markPublished returns (on another thread), or the server could time out callbacks. Our fix, no lock during callbacks, is the root-cause fix.
