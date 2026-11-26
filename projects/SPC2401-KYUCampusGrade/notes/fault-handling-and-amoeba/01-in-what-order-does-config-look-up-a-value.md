# 01. In what order does Config look up a value?

*Topic: Config – no hard-coded host or port*

First a JVM property `-Dkyu.<key>`, then an environment variable `KYU_<KEY>`, then the file `config/kyu.properties`, and only then the default passed by the caller. The first non-blank value wins.
