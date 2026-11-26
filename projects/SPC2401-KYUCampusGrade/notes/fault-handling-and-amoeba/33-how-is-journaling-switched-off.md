# 33. How is journaling switched off?

*Topic: Journal – surviving a crash*

Set `server.journal` to blank or `none`; `Journal.open` then returns a disabled journal whose appends do nothing. Tests and the benchmark use this.
