set path=%path%;%~dp0software\jdk1.8.0_301\bin
set path=%path%;%~dp0software\jdk1.8.0_301\jre\bin
set path=%path%;%~dp0software\apache-tomcat-9.0.65\bin
set path=%path%;%~dp0software\apache-maven-3.8.1\bin

rem ================================================================
rem 需要设置好Tomcat的端口号和目录
set TOMCAT_PORT=9003
set CATALINA_HOME=%~dp0software\apache-tomcat-9.0.65
set TARGET_HOME=%~dp0target
set WEBAPP_HOME=%~dp0software\apache-tomcat-9.0.65
rem ================================================================

echo.
echo.
echo "======================== bat code ========================"
echo.
echo.
call mvn clean install
mkdir %WEBAPP_HOME%\webapps
move %TARGET_HOME%\authenticate-1.0-SNAPSHOT.war    %WEBAPP_HOME%\webapps\authenticate-1.0-SNAPSHOT.war
rem 启动脚本文件地址
set STARTUP_BAT=%CATALINA_HOME%\bin\startup.bat

rem 初始化缓存和日志目录(重启服务的时候需要清除缓存和日志)
set WORK_DIR=%CATALINA_HOME%\work\Catalina
set CONF_DIR=%CATALINA_HOME%\conf\Catalina
set LOGS_DIR=%CATALINA_HOME%\logs

rem echo.表示是空行，echo和.中间不能有空格
echo "============ environment variable ============"
echo [CATALINA_HOME]=%CATALINA_HOME%
echo [TOMCAT_PORT]=%TOMCAT_PORT%
echo [STARTUP_BAT]=%STARTUP_BAT%
echo "============ environment variable ============"
echo "============ cache logs dir ============"
echo [WORK_DIR]=%WORK_DIR%
echo [CONF_DIR]=%CONF_DIR%
echo [LOGS_DIR]=%LOGS_DIR%
echo "============ cache logs dir ============"

rem 调用启动脚本.
echo "============ start server ============"
call %STARTUP_BAT%
echo %CATALINA_HOME% is started ...
echo "============ start server ============"

rmdir /s/q %TARGET_HOME%
pause