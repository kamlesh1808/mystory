#!/bin/bash

###
### clone git repo:
### clean gitrepos dir, clone git repo
###
printf "clone git repo\n"

now="$(date)"
printf "Current date and time: %s\n" "$now"
printf "OS Type: $OSTYPE\n"
printf "pwd: $PWD\n\n"

printf "Start rm -r gitrepos.\n"
cd ~/dev
sudo rm -r gitrepos

if [ $? == 0 ]; then
  printf "Completed rm -r gitrepos.\n"
fi

mkdir gitrepos
cd gitrepos

printf "pwd: $PWD\n\n"

printf "Start cloning Gitlab Git repository\n"

git clone git@gitlab.com:mystorywork/mystory.git

cd ~/dev/gitrepos/mystory

git remote -v

git branch -v -a

printf "Completed cloning Gitlab Git repository\n"