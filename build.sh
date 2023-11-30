#!/bin/bash

###
### build myStory:
### checkout entered branch, gradle clean build, mvn clean package
###
printf "build myStory\n"

now="$(date)"
printf "Current date and time: %s\n" "$now"
printf "OS Type: $OSTYPE\n"
printf "pwd: $PWD\n\n"


if [[ "$OSTYPE" == "linux-gnu" ]]; then
  # cd to gradle wrapper directory
  cd ~/dev/gitrepos/mystory
  printf "pwd: $PWD\n\n"
elif [[ "$OSTYPE" == "msys" ]]; then
  printf "pwd: $PWD\n\n"
fi

read -p 'Enter git branch name: ' -e branchName
printf "git branch name is $branchName\n"

printf "Start git checkout branch name $branchName\n"
git checkout -b $branchName origin/$branchName --force
printf "Completed git checkout\n"

chmod +x *.sh
chmod +x gradlew

printf "Start gradlew clean build\n"

./gradlew clean build
printf "Completed gradlew clean build\n"

#printf "Start mvn clean package\n"
#mvn clean package
#printf "Completed mvn clean package\n"

printf "Completed build.\n"
