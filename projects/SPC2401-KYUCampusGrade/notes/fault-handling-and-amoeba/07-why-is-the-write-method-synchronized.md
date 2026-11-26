# 07. Why is the write method synchronized?

*Topic: EventLog – the timestamped failure log*

Many threads log at once (RMI, worker and notifier threads). Synchronising keeps every line whole instead of interleaving two messages character by character.
