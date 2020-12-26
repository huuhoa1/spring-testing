#!/bin/bash
export WEATHER_API_KEY=d1fa757ce6e36e1767e16a3dd7bba01b
export JAVA_ARGS="-Xmx4G"
java $JAVA_ARGS \
    -javaagent:/app/opentracing-specialagent-1.7.4.jar \
    -Dsa.exporter=wavefront \
    -Dwf.application=myApplication \
    -Dwf.service=myService \
    -Dwf.reportingMechanism=direct \
    -Dwf.server=https://longboard.wavefront.com \
    -Dwf.token=ec2261d0-d1f2-41e1-94a9-add66b4e3d7a \
    -jar /app/app.jar
