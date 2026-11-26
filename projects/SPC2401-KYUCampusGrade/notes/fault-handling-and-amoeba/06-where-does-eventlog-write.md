# 06. Where does EventLog write?

*Topic: EventLog – the timestamped failure log*

To standard output and, in append mode, to `<log.dir>/<component>.log`, e.g. `logs/server.log`. The writer flushes every line, so a crash does not lose the last entries.
