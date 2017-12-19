#!/bin/sh

APP_NAME=kjv-service
set JAVA_CMD=`which java`
set JAVA_OPTS=
APP_HOME=/opt/${APP_NAME}
set PROG_OPTS=-Dlogback.configurationFile=${APP_HOME}/logback.groovy
echo Running: ${JAVA_CMD} ${JAVA_OPTS} ${PROG_OPTS} ${APP_HOME}/${APP_NAME}.jar
${JAVA_CMD} ${JAVA_OPTS} ${PROG_OPTS} ${APP_HOME}/${APP_NAME}.jar > ${APP_HOME}/run.log

echo ${APP_NAME} Service Started ${PID}

