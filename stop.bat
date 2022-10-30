set STOP_BAT_BACKGROUD=%~dp0Java_Authenticate
call %STOP_BAT_BACKGROUD%\stop.bat
for /f "tokens=5" %%a in ('netstat /ano ^| findstr 9000') do taskkill /F /pid %%a


