#!/bin/sh

cd /service/earthquake/lib

PID=`ps -ef | grep EarthquakAlarm | grep -v grep | awk '{print $2}'`

if [ -n "$PID" ] ; then
    echo "[$PID] ${P_NAME} already EarthquakAlarm!"
else
	java -jar -Dp.name=EarthquakAlarm -Dspring.profiles.active=prod earthquake-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
    echo "Starting EarthquakAlarm........................."
fi
