#!/bin/bash
printf "deploy MyStory\n"

now="$(date)"
printf "Current date and time: %s\n" "$now"
printf "OS Type: $OSTYPE\n"
printf "pwd: $PWD\n\n"


###
### collect undeploy deploy info
###

printf "Started collecting required info for undeploy and deploy\n"
hostIP=$(curl ifconfig.me)  # linux

if [ -z "$hostIP" ]; then
  hostIP=127.0.0.1
  printf "Using default hostIP: $hostIP\n"
else
  printf "Host IP is: $hostIP\n"
fi

read -p 'Enter application server port or press enter to accept default 9923: ' -e port

if [ -z "$port" ]; then
	port=9923
	printf "Using default port: $port\n"
else
	printf "Port is: $port\n"	      
fi


###
### undeploy
###
read -p 'Enter undeploy release name: ' -e undeployReleaseName

read -p 'Enter undeploy build type M for MAVEN or press enter for GRADLE (default): ' -e undeployBuildType

if [ -z "$undeployBuildType" ]; then
	undeployBuildType=G
	printf "Using default deploy build type: $undeployBuildType\n"
else
	printf "undeploy build type is: $undeployBuildType\n"
fi

printf "Started undeploy release: $undeployReleaseName undeploy build type:$undeployBuildType from pwd: $PWD\n"

jboss-cli.sh -c controller=$hostIP:$port --command="undeploy mystory-ear-$undeployReleaseName-$undeployBuildType.ear"
printf "Completed undeploy: $undeployReleaseName\n\n"


###
### deploy
###

read -p 'Enter deploy release name: ' -e deployReleaseName
read -p 'Enter deploy build type M for MAVEN or press enter for GRADLE (default): ' -e deployBuildType

if [ -z "$deployBuildType" ]; then
	deployBuildType=G
	printf "Using default deploy build type: $deployBuildType\n"
else
	printf "deploy build type is: $deployBuildType\n"
fi

printf "Started deploy release: $deployReleaseName deploy build type:$deployBuildType from pwd: $PWD\n"

if [[ "$OSTYPE" == "linux-gnu" ]]; then
  if [ "$deployBuildType" == "G" ]; then
    jboss-cli.sh -c controller=$hostIP:$port --command="deploy ~/dev/gitrepos/mystory/mystory-ear/build/libs/mystory-ear-$deployReleaseName-$deployBuildType.ear"
  fi

  if [ "$deployBuildType" == "M" ]; then
    jboss-cli.sh -c controller=$hostIP:$port --command="deploy ~/dev/gitrepos/mystory/mystory-ear/target/mystory-ear-$deployReleaseName-$deployBuildType.ear"
  fi

elif [[ "$OSTYPE" == "msys" ]]; then
  if [ "$deployBuildType" == "G" ]; then
    jboss-cli.sh -c controller=$hostIP:$port --command="deploy ./mystory-ear/build/libs/mystory-ear-$deployReleaseName-$deployBuildType.ear"
  fi

  if [ "$deployBuildType" == "M" ]; then
    jboss-cli.sh -c controller=$hostIP:$port --command="deploy ./mystory-ear/target/mystory-ear-$deployReleaseName-$deployBuildType.ear"
  fi
fi

printf "Completed deploy $deployReleaseName\n\n"