# 21. How does a String parameter travel?

*Topic: Section 1 – Remote invocation*

By value. Java serialises the characters (TC_STRING, length, UTF bytes), and the server works on its own copy. Changing it on one side never affects the other.
