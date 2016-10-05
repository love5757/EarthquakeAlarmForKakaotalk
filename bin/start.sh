#!/bin/sh

cd /service/earthquake/lib

java -jar -Dp.name=EarthquakAlarm -Dspring.profiles.active=prod earthquake-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &