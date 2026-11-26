#!/usr/bin/env sh
# Runs every JUnit test and writes XML reports to evidence/test-reports.
set -e
cd "$(dirname "$0")/.."
SEP=":"; case "$(uname -s)" in MINGW*|MSYS*|CYGWIN*) SEP=";";; esac
java -Dstdout.encoding=UTF-8 -jar lib/junit-platform-console-standalone-1.10.2.jar execute \
  --class-path "out/classes${SEP}out/test-classes" --scan-class-path \
  --reports-dir evidence/test-reports --details=tree "$@"
