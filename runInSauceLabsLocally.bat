@echo off
set SAUCE=true
set SAUCE_USERNAME=oauth-mememailbox2020-7afbb
set SAUCE_ACCESS_KEY=d2b36968-7d4b-4dc3-9363-5cc39d83bf5c
set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005
mvn clean test
pause