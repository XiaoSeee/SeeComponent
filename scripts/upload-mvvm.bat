@echo off
cd..

echo ִ������...
call gradlew clean

echo ����...
call gradlew :MVVMLibrary:build

echo �ϴ�...
call gradlew :MVVMLibrary:publishReleasePublicationToMavenCentralRepository

pause