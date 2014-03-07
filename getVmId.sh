#!/bin/bash

xl list | grep $1 | tr -s ' ' |cut -d ' ' -f 2