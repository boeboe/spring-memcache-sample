#!/bin/bash
#
# Helper script to deploy local war file onto Jetty or Tomcat.
#

# set -x

export PORT='9090'
export BROWSER_URL='http://localhost:'${PORT}'/rest/persons'
export BROWSER_URL_CACHE='http://localhost:'${PORT}'/rest/cache-persons'

sleep 10 
firefox ${BROWSER_URL} &> /dev/null &
firefox ${BROWSER_URL_CACHE} &> /dev/null &


java -jar target/dependency/jetty-runner.jar --port ${PORT} target/*.war
#java -jar target/dependency/webapp-runner.jar --port ${PORT} --expand-war target/*.war
