grammar ReachLang;

requirements : test* ;

test    : 'reach from'? entrance (ARROW middle)* ARROW exit '.' ;

entrance: (CLIENT|INTERNET) condition;
middle  : nport condition ;
exit    : (CLIENT|INTERNET) condition;

condition   : trafficdesc? (';' invariant)? ;
trafficdesc : constraint ('&&' constraint)* ;
invariant : field ('&&' field)* ;

constraint : ipconstraint | l4constraint | protoconstraint ;

ipfield  : 'src' | 'dst' ;
ipconstraint : ipfield (mask | ipv4) ;

l4field  : 'dst port' | 'src port' ;
l4constraint : l4field (range | NUMBER);

protoconstraint : 'tcp' | 'udp' ;

field   : (ipfield | l4field | 'proto') ;

nport    : ID ':' ID (':' NUMBER)? ;
port    : NUMBER ;
ipv4    : NUMBER ('.' NUMBER) ('.' NUMBER) ('.' NUMBER);
mask    : ipv4 '/' NUMBER ;
range   : NUMBER '-' NUMBER ;
NUMBER  : [0-9]+ ;
CLIENT  : 'client' ;
INTERNET    : 'internet' ;
ID      : [0-9a-zA-Z\-_]+ ;

ARROW   : '->' ;

WS		: [ \t\r\n]+ -> skip ;