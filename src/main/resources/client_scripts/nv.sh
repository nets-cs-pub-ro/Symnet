#!/bin/bash

# Syntax:
# 1. Start VM: start identity_file click_file requirements_file vm_name server_addr
# 2. Stop VM: stop identity_file vm_name



case $1 in
	"start" )
		echo "Start VM request"
		server=${6:-"http://localhost:8080/file"}
		curl -F id=@$2 -F click_file=@$3 -F requre=@$4 -F name=$5 ${server}
		;;
	"stop" )
		echo "Stop VM request"
		;;
	* )
		echo "Unknown command received"
		;;
esac