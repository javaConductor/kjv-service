#!/bin/sh

APP_NAME=kjv-service
#echo java=`which java`
export JAVA_CMD=`which java`
JAVA_OPTS=
APP_HOME=/opt/${APP_NAME}
PROG_OPTS=-Dlogback.configurationFile=${APP_HOME}/logback.groovy
echo Running: ${JAVA_CMD} ${JAVA_OPTS} ${PROG_OPTS} -jar ${APP_HOME}/${APP_NAME}.jar
${JAVA_CMD} ${JAVA_OPTS} ${PROG_OPTS} -jar ${APP_HOME}/${APP_NAME}.jar > ${APP_HOME}/run.log

echo ${APP_NAME} Service Started

