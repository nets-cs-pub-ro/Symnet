{
    if (index($2,".")==5){
	print $0;

	for (i=1;i<mul;i++){
	    split($2,mac,"\.");
	    printf("%s\t%s.%s.%d%d%d%d\t%s\t%s\n", $1,mac[1],mac[2],int(i/1000),int(i/100),int(i/10),i%10,$3,$4)
	}
    }
}
END{

}
