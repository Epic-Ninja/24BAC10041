@echo off
echo Java Library Management System
call mvn clean package
if errorlevel 1 exit /b 1
call mvn exec:java
