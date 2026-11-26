# 70. Why does GradeServiceImpl have no synchronized methods?

*Topic: My code (WP1)*

A synchronised method would lock the whole object, which causes the 4.2 deadlock and makes every reader wait. The shared state lives in classes with finer locks: a read/write lock and concurrent maps.
