# Compiles the source and the tests into out\. Needs JDK 17+ on PATH.
$ErrorActionPreference = 'Stop'
Set-Location (Join-Path $PSScriptRoot '..')
$junit = 'lib\junit-platform-console-standalone-1.10.2.jar'
if (-not (Test-Path $junit)) {
  New-Item -ItemType Directory -Force lib | Out-Null
  Invoke-WebRequest -OutFile $junit 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar'
}
if (Test-Path out) { Remove-Item -Recurse -Force out }
$src = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName }
javac -d out\classes $src
if ($LASTEXITCODE -ne 0) { exit 1 }
$tests = @(Get-ChildItem -Recurse tests -Filter *.java -ErrorAction SilentlyContinue | ForEach-Object { $_.FullName })
if ($tests.Count -gt 0) {       # early in the project there may be no tests yet
  javac -d out\test-classes -cp "out\classes;$junit" $tests
  if ($LASTEXITCODE -ne 0) { exit 1 }
}
Write-Host 'Build OK -> out\classes, out\test-classes'
