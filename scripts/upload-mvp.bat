@echo off
cd..

echo ִ������...
call gradlew clean

echo ����...
call gradlew :MVPLibrary:build

echo �ϴ�...
call gradlew :MVPLibrary:publishReleasePublicationToMavenCentralRepository

pause