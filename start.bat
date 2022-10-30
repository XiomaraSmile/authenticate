set STARTUP_BAT_FRONT=%~dp0Vue_Authenticate
cd  %STARTUP_BAT_FRONT%
start %STARTUP_BAT_FRONT%\start.bat

set STARTUP_BAT_BACKGROUD=%~dp0Java_Authenticate
cd %STARTUP_BAT_BACKGROUD%
call %STARTUP_BAT_BACKGROUD%\start.bat
pause


