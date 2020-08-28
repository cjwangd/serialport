echo off

set cwd=%~dp0
set path=%cwd%jre\bin;%path%
cd %cwd%
start javaw.exe -jar serialport-1.0.RELEASE.jar