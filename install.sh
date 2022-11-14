#!/bin/sh 
shopt -s expand_aliases

# Create a folder in /usr/bin/
cd /usr/bin && mkdir mgit 
# cd into mgit dir
cd mgit

# Download mgit.jar
wget  "https://raw.githubusercontent.com/shaikhamaan/mgit/main/mgit.jar"

# Download mgit.sh
wget "https://raw.githubusercontent.com/shaikhamaan/mgit/main/mgit.sh"

# Add execution permission to mgit.sh
chmod +x mgit.sh

# Add path to .bashrc
export PATH=$PATH:/usr/bin/mgit

# Creating an alias mgit to easily execute commands
alias mgit="cd /usr/bin/mgit && sh ./mgit.sh"