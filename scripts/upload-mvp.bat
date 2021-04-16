@echo off
cd..

echo 执行清理...
call gradlew clean

echo 编译...
call gradlew :MVPLibrary:build

echo 上传...
call gradlew :MVPLibrary:publishReleasePublicationToMavenCentralRepository

pause