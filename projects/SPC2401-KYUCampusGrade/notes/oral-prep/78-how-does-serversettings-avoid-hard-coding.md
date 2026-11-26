# 78. How does ServerSettings avoid hard-coding?

*Topic: My code (WP1)*

Every value comes from `Config`: first `-Dkyu.*` JVM properties, then `KYU_*` environment variables, then `config/kyu.properties`, and only then a default. Tests build their own settings object.
