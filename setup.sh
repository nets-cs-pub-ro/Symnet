#!/bin/bash

# Install OpenJDK-8
#
# If a different version is preferred, the ScalaZ3 jar must be recomplied
# against this jdk. You're on shaky ground there.
sudo apt-get update
sudo apt-get install -y python-software-properties
sudo add-apt-repository -y ppa:openjdk-r/ppa
sudo apt-get update
sudo apt-get install -y openjdk-8-jdk 

# We use sbt for building Symnet.
#
# If the link is broken, search sbt on google and grab the latest version
# from there.
wget https://dl.bintray.com/sbt/native-packages/sbt/0.13.9/sbt-0.13.9.tgz 
tar xf sbt-0.13.9.tgz 
rm *.tgz
# Make sure sbt is in path.
sudo ln -s ~/sbt/bin/sbt /usr/local/bin/sbt

# Install git and grab Symnet
sudo apt-get install -y git-core 
git clone https://github.com/radustoenescu/Symnetic.git

# Build it and test the rig.
cd Symnetic/
sbt compile test
sbt sample
