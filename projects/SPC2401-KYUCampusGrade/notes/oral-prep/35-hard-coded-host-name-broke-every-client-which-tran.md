# 35. Hard-coded host name broke every client. Which transparency, and what fix?

*Topic: Section 3 – OS types, processes, threads*

Location (migration) transparency. Bind by name in the registry and read the registry host from configuration, as `Config` does with `registry.host`.
