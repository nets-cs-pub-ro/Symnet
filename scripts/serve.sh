#!/bin/bash
ncat -l 6000 --keep-open --sh-exec "(/bin/cat > middle; ./contraption.sh)"
