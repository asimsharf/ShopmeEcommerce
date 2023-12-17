#!/bin/bash
export JAVA_HOME=/usr/java/jdk1.8.0_131
export PATH=$JAVA_HOME/bin:$PATH
APP_NAME=shopme
nohup java -jar /path/to/shopme.jar.jar > shopme.log 2>&1 &
echo "$APP_NAME is running"