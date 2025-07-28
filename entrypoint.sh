#!/bin/sh
set -e

envsubst < /app/config/prometheus/prometheus.yml.template > /app/config/prometheus/prometheus.yml

exec java -jar /app/app.jar
