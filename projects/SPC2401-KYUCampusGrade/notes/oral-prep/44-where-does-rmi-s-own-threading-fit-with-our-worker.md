# 44. Where does RMI's own threading fit with our worker pool?

*Topic: Section 3 – OS types, processes, threads*

RMI accepts each connection on its own thread; that thread submits the request to our fixed worker pool and waits. The pool, not RMI, limits how many requests run at the same time.
