#!/bin/sh

P_NAME='EarthquakAlarm'

PID=`ps -ef | grep ${P_NAME} | grep -v grep | awk '{print $2}'`

if [ -n "$PID" ] ; then
	kill -9 ${PID}
	echo "[${PID}] stopped ${P_NAME}........................."
else 
	echo "[$PID] ${P_NAME} dose not exist!"
fi 
