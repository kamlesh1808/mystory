#!/bin/bash

###
### start WildFly
###
printf "start WildFly\n"

now="$(date)"
printf "Current date and time: %s\n" "$now"
printf "OS Type: $OSTYPE\n"
printf "pwd: $PWD\n\n"

printf "starting WildFly...\n"
ip=$(hostname -I)
printf "ip: $ip\n"
ip=$(curl ifconfig.me)
printf "ip: $ip\n"
standalone.sh --server-config=standalone-full.xml &
