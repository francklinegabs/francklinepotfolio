# 03. Which properties file does Config read, and can it be changed?

*Topic: Config – no hard-coded host or port*

`config/kyu.properties` relative to the working directory by default. Another file can be chosen with `-Dkyu.config=path`. If the file is missing, Config simply falls back to defaults.
