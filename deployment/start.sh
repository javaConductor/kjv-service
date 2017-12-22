#!/bin/sh
#Created Dec 22, 2017 00:42:15

APP_NAME='kjv-service'
#echo java=`which java`

APP_HOME=/opt/kjv-service
java -Dlogging.config=/opt/kjv-service/logback.groovy -jar /opt/kjv-service/kjv-service.jar  > /opt/kjv-service/run.log 2> /opt/kjv-service/run.log

echo kjv-service Service Started
