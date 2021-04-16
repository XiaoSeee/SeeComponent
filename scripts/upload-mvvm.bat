@echo off
cd..

echo 执行清理...
call gradlew clean

echo 编译...
call gradlew :MVVMLibrary:build

echo 上传...
call gradlew :MVVMLibrary:publishReleasePublicationToMavenCentralRepository

pause