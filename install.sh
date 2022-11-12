#!/bin/sh

# Create a folder in /usr/bin/
cd /usr/bin && mkdir mgit && cd mgit

# Download mgit.kt
wget -L "https://github.com/shaikhamaan/mgit/blob/main/mgit.kt"

# Download mgit.sh
wget -L "https://github.com/shaikhamaan/mgit/blob/main/mgit.sh"

# Create jar from mgit.kt
kotlinc mgit.kt -include-runtime -d mgit.jar

# Add execution permission to mgit.sh
chmod +x mgit.sh

# Add path to .bashrc
export PATH=$PATH:/usr/bin/mgit

# Creating an alias mgit to easily execute commands
alias mgit="sh ./mgit.sh"