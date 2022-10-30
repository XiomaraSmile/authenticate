set path=%path%;%~dp0software\jdk1.8.0_301\bin
set path=%path%;%~dp0software\jdk1.8.0_301\jre\bin
set path=%path%;%~dp0software\apache-tomcat-9.0.65\bin
set path=%path%;%~dp0software\apache-maven-3.8.1\bin
rem ================================================================
rem 需要设置好Tomcat的端口号和目录
set TOMCAT_PORT=9003
set CATALINA_HOME=%~dp0software\apache-tomcat-9.0.65
rem ================================================================

echo "======================== bat code ========================"
set SHUTDOWN_BAT=%CATALINA_HOME%\bin\shutdown.bat

rem 初始化缓存和日志目录(重启服务的时候需要清除缓存和日志)
set WORK_DIR=%CATALINA_HOME%\work
set CONF_DIR=%CATALINA_HOME%\conf\Catalina
set LOGS_DIR=%CATALINA_HOME%\logs

rem echo.表示是空行，echo和.中间不能有空格
echo.
echo.
echo "============ environment variable ============"
echo [CATALINA_HOME]=%CATALINA_HOME%
echo [TOMCAT_PORT]=%TOMCAT_PORT%
echo [STARTUP_BAT]=%STARTUP_BAT%
echo [SHUTDOWN_BAT]=%SHUTDOWN_BAT%
echo "============ environment variable ============"
echo "============ cache logs dir ============"
echo [WORK_DIR]=%WORK_DIR%
echo [CONF_DIR]=%CONF_DIR%
echo [LOGS_DIR]=%LOGS_DIR%
echo "============ cache logs dir ============"


rem 调用停止脚本来停掉服务.
echo "============ shutdown server ============"
call %SHUTDOWN_BAT%
echo %CATALINA_HOME% is killed ...

rem 由于服务没有完全关闭(服务停了，但是窗口没有关闭，因此通过监听端口号并杀死进程来关闭窗口).
for /f "tokens=5" %%a in ('netstat /ano ^| findstr %TOMCAT_PORT%') do taskkill /F /pid %%a
echo "============ shutdown server ============"
rem 清除缓存和日志文件.
echo.
echo.
echo "============ clear Catalina-work   Catalina-conf   logs============"
rmdir /s/q %WORK_DIR%
rmdir /s/q %CONF_DIR%
rmdir /s/q %LOGS_DIR%
rmdir /s/q %CATALINA_HOME%\webapps