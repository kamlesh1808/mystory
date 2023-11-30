#!/bin/bash
printf "push Gitlab myStory\n"

now="$(date)"
printf "Current date and time: %s\n" "$now"


printf "pwd is $PWD\n"


read -p 'Enter git branch name: ' -e branchName

printf "Start git pull -r glmystory $branchName\n"
git pull -r glmystory $branchName

printf "Start git push glmystory $branchName\n"

git push glmystory $branchName -f

printf "Completed push.\n"


