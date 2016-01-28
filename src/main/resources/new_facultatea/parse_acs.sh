cat $1 | awk '{print $1,$2, $4, $5;}'| sed s/TenGigabitEthernet/Ten/g | sed s/GigabitEthernet/Gi/g | sed s/Port-channel/Po/g
