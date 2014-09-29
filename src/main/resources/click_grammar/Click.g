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

startOfPath : entryPort? pathElement exitPort? ;
inTheMiddle : ARROW entryPort? pathElement exitPort? ;
endOfPath   : ARROW entryPort? pathElement exitPort? ;

pathElement : elementName
            | newElement
            ;

entryPort   : port ;
exitPort    : port ;

port            : '[' portId ']' ;

portId          : NUMBER ;

CONJUNCTION                 : '&&' ;
NON_CAPITALIZED_STRING      : [a-z]([a-zA-Z@_0-9\-/\\:\.])* ;
CAPITALIZED_STRING          : [A-Z]([a-zA-Z@_0-9\-/\\:\.])* ;
DASH                        : '-' ;
NUMERIC_CONF_PARAM          : [0-9a-fA-F]+ (('/' | '.' | ':' | 'x' | 'X') [0-9a-fA-F]+)+ ;

ARROW   : '->'
        | '=>'
        ;

NUMBER  : [0-9]+ ;
ANY_STRING : [a-zA-Z0-9\.@_/]+ ;

DEFINE_SYMBOL : '::' ;

NEWLINE : '\n' ;

WS		: [ \t\r]+ -> skip ;

