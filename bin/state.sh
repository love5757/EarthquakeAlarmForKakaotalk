#!/bin/sh

P_NAME='EarthquakAlarm'

PID=`ps -ef | grep p.name=${P_NAME} | grep -v grep | awk '{print $2}'`

if [ -z "$PID" ] ; then
	echo ${P_NAME} nothing.....................
else 
	echo "[$PID] ${P_NAME} is running............................."
fi