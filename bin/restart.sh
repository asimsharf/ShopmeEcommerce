#!/bin/bash
export JAVA_HOME=/usr/java/jdk1.8.0_131
export PATH=$JAVA_HOME/bin:$PATH
APP_NAME=shopme
pid=`ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}'`

if [ -z "${pid}" ]; then
   echo "$APP_NAME is not running"
else
    echo "kill thread...$pid"
    kill -9 $pid
fi

nohup java -jar /path/to/shopme.jar > shopme.log 2>&1 &
echo "$APP_NAME is running"