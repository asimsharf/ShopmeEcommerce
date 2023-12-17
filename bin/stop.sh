#!/bin/bash
APP_NAME=xxx
pid=`ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}'`

if [ -z "${pid}" ]; then
   echo "$APP_NAME is not running"
else
    echo "kill thread...$pid"
    kill -9 $pid
fi