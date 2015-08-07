#!/bin/bash

(echo $(wc -l $1 | cut --delimiter=" "  -f 1); cat $1) | ncat $2 $3
