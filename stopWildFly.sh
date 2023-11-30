#!/bin/bash

###
### stop WildFly
###
printf "stop WildFly\n"

now="$(date)"
printf "Current date and time: %s\n" "$now"
printf "OS Type: $OSTYPE\n"
printf "pwd: $PWD\n\n"

printf "stopping WildFly...\n"
printf "ip: $ip\n"
ip=$(hostname -I)
curl ifconfig.me
if [ -z "$ip" ]
then
  ip=127.0.0.1
  printf "ip: $ip\n"
fi
jboss-cli.sh -c controller=$ip --command="shutdown"
