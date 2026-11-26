# 02. How is a key turned into an environment variable name?

*Topic: Config – no hard-coded host or port*

`envName` adds `KYU_`, upper-cases the key and replaces dots with underscores, so `registry.host` becomes `KYU_REGISTRY_HOST`.
