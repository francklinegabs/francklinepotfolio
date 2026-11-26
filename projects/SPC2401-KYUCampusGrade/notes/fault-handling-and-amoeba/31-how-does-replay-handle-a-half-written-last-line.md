# 31. How does replay handle a half-written last line?

*Topic: Journal – surviving a crash*

Lines with the wrong number of fields are ignored, and a NumberFormatException on a torn line is caught and skipped, so recovery never fails because of the crash itself.
