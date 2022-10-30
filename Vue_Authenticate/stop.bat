set path=%path%;%~dp0software\node-v18.12.0-win-x64
for /f "tokens=5" %%a in ('netstat /ano ^| findstr 9000) do taskkill /F /pid %%a
pause