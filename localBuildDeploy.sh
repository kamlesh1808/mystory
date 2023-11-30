#!/bin/bash

###
### local build and deploy:
### gradle clean build, deploy
###
printf "local build and deploy\n"

now="$(date)"
printf "Current date and time: %s\n" "$now"
printf "OS Type: $OSTYPE\n"
printf "pwd: $PWD\n\n"

printf "Start gradlew clean build\n"
./gradlew clean build
printf "Completed gradle clean build\n"

printf "Start local deploy.\n"
jboss-cli.sh -c controller=127.0.0.1:9990 --command="deploy --force ./mystory-ear/build/libs/mystory-ear-5.22.02.23-G.ear"

printf "Completed local build and deploy.\n"