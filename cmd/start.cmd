echo off
echo %~dp0
set cwd=%~dp0
start %cwd%\jre\bin\javaw.exe -jar %cwd%\serialport-1.0.RELEASE.jar