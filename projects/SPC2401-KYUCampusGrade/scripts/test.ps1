# Runs every JUnit test and writes XML reports to evidence\test-reports.
Set-Location (Join-Path $PSScriptRoot '..')
java -Dstdout.encoding=UTF-8 -jar lib\junit-platform-console-standalone-1.10.2.jar execute `
  --class-path 'out\classes;out\test-classes' --scan-class-path `
  --reports-dir evidence\test-reports --details=tree @args
