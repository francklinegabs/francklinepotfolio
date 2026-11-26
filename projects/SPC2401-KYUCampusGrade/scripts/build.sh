#!/usr/bin/env sh
# Compiles the source and the tests into out/. Needs JDK 17+ on PATH.
# JUnit is downloaded once into lib/ (it is not committed).
set -e
cd "$(dirname "$0")/.."
JUNIT=lib/junit-platform-console-standalone-1.10.2.jar
if [ ! -f "$JUNIT" ]; then
  mkdir -p lib
  curl -sSL -o "$JUNIT" https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar
fi
SEP=":"; case "$(uname -s)" in MINGW*|MSYS*|CYGWIN*) SEP=";";; esac
rm -rf out
javac -d out/classes $(find src -name '*.java')
TESTS=$(find tests -name '*.java' 2>/dev/null || true)
if [ -n "$TESTS" ]; then
  javac -d out/test-classes -cp "out/classes${SEP}${JUNIT}" $TESTS
fi
echo "Build OK -> out/classes, out/test-classes"
