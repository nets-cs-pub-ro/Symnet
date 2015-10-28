[SymNet][symnet] v2
=========

Symbolic execution for middleboxes made easy and fast.

### Setup (nix machine)

1. JDK 8 (in case a different one is used the ScalaZ3 jar needs to be rebuilt against this, different, JDK)
2. sbt - The simple build tool for Scala projects

See _setup.sh_ for a concrete setup script (it was tested on 64bit Ubuntu 12.04, 14.04 and 15.10).        

Then from project root issue _sbt test_.

### Setup (Vagrant)

There is also a _Vagrantfile_ if you prefer this option. This also uses _setup.sh_ for provisioning.

### SEFL sample

See _src/main/scala/change/v2/runners/experiments/SEFLRunner.scala_ to start experimenting with SEFL. _sbt sample_ will run that code.

[symnet]: http://nets.cs.pub.ro/~costin/files/symnet-techreport.pdf 
