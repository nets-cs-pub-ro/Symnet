grammar Click;

configFile : line+ ;

line    : component (';' component)* ';'* NEWLINE?
        | NEWLINE
        ;

component   : newElement
            | path
            ;

newElement    : ( elementName DEFINE_SYMBOL )? elementInstance ;

elementInstance : className config? ;

configParameter : (CAPITALIZED_STRING | NON_CAPITALIZED_STRING | NUMERIC_CONF_PARAM | DASH | NUMBER | CONJUNCTION)+ ;

config  : '(' ( configParameter (',' configParameter)* )? ')' ;

className : CAPITALIZED_STRING ;

elementName : NON_CAPITALIZED_STRING ;

path        : startOfPath inTheMiddle*  endOfPath ;

startOfPath : pathElement port? ;
inTheMiddle : ARROW port? pathElement port? ;
endOfPath   : ARROW port? pathElement ;

pathElement : elementName
            | newElement
            ;

port            : '[' portId ']' ;

portId          : NUMBER ;

CONJUNCTION                 : '&&' ;
NON_CAPITALIZED_STRING      : [a-z]([a-zA-Z@_0-9\-/:])* ;
CAPITALIZED_STRING          : [A-Z]([a-zA-Z@_0-9\-/:])* ;
DASH                        : '-' ;
NUMERIC_CONF_PARAM          : [0-9a-fAA-F]+ (('/' | '.' | ':' | 'x' | 'X') [0-9a-fAA-F]+)+ ;

ARROW   : '->'
        | '=>'
        ;

NUMBER  : [0-9]+ ;
ANY_STRING : [a-zA-Z0-9\.@_/]+ ;

DEFINE_SYMBOL : '::' ;

NEWLINE : '\n' ;

WS		: [ \t]+ -> skip ;

