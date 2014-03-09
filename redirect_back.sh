#!/bin/bash

echo $2
echo $3

of_port()
{
	#local interface
	local if_name=$1
	ovs-ofctl show xenbr0 | grep $if_name | cut -d '(' -f 1 | cut -d ' ' -f 2

}

PORT1=$(of_port $2)

case $1 in
start)
	PORT2=$(of_port $3)
	echo "ovs-ofctl add-flow xenbr0 in_port=$PORT1,idle_timeout=0,action=output:$PORT2"
	ovs-ofctl add-flow xenbr0 in_port=$PORT1,idle_timeout=0,action=output:$PORT2
	;;
stop)
    PORT2=$(of_port $3)
	echo "ovs-ofctl del-flows xenbr0 in_port=$PORT1,out_port=$PORT2"
	ovs-ofctl del-flows xenbr0 in_port=$PORT1,out_port=$PORT2
	;;
esac