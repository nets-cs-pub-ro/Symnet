#!/bin/bash
cat prefix middle suffix> ../src/main/scala/org/change/v2/runners/Run.scala

cd ..
sbt interpret
