#!/usr/bin/env bash
TARGET_NAME=$1
VERSION=$2
APP_HOME=/opt/${TARGET_NAME}
if [ -d  ${APP_HOME} ]
then
    rm -r ${APP_HOME}
fi

if [ ! -d  ${APP_HOME} ]
then
    mkdir ${APP_HOME}
    mkdir ${APP_HOME}/systemd
fi

rm /usr/bin/${TARGET_NAME}.* 2> /dev/null

# Jar file
cp ./../build/libs/${TARGET_NAME}-${VERSION}.jar ${APP_HOME}/${TARGET_NAME}.jar
chmod +x ${APP_HOME}/${TARGET_NAME}.jar

# Start Script
cp ./start.sh ${APP_HOME}/systemd/${TARGET_NAME}.start.sh
chmod +x ${APP_HOME}/systemd/${TARGET_NAME}.start.sh

# Stop Script
#cp ./stop.sh /usr/bin/${TARGET_NAME}.stop.sh
cp ./stop.sh ${APP_HOME}/systemd/${TARGET_NAME}.stop.sh
chmod +x ${APP_HOME}/systemd/${TARGET_NAME}.stop.sh

# Logging Config
cp ./logback.groovy ${APP_HOME}/logback.groovy

# systemd service file
cp ./${TARGET_NAME}.service /etc/systemd/system/${TARGET_NAME}.service
