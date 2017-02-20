grammar P4Grammar;

// TODO: Watch for "[" or "]"

p4_program      :   p4_declaration+ ;

p4_declaration  :    header_type_declaration
                |   instance_declaration
                |   field_list_declaration
                |   field_list_calculation_declaration
                |   calculated_field_declaration
                |   value_set_declaration
                |   parser_function_declaration
                |   parser_exception_declaration
                |   counter_declaration
                |   meter_declaration
                |   register_declaration
                |   action_function_declaration
                |   action_profile_declaration
                |   action_selector_declaration
                |   table_declaration
                |   control_function_declaration
                ;

const_value     :   ('+'|'-')? width_spec? unsigned_value ;
unsigned_value  :   binary_value | decimal_value | hexadecimal_value ;

binary_value    :   BINARY_BASE binary_digit+ ;
decimal_value   :   decimal_digit+ ;
hexadecimal_value   :   HEXADECIMAL_BASE hexadecimal_digit+ ;

BINARY_BASE     :   '0b' | '0B' ;
HEXADECIMAL_BASE    :   '0x' | '0X' ;

binary_digit    :   '_' | '0' | '1' ;
decimal_digit   :   binary_digit | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' ;
hexadecimal_digit   : decimal_digit | 'a' | 'A' | 'b' | 'B' | 'c' | 'C' | 'd' | 'D' | 'e' | 'E' | 'f' | 'F' ;

width_spec  :   decimal_digit+ '’' ;
field_value :   const_value ;
header_type_declaration :   'header_type' header_type_name '{' header_dec_body '}' ;
header_type_name : NAME ;

header_dec_body :   'fields' '{' field_dec+ '}' ( 'length' ':' length_exp ';' )? ( 'max_length' ':' const_value ';' )? ;

field_dec   :   field_name ':' bit_width ( '(' field_mod ')' )? ';' ;
field_name  :   NAME ;
field_mod   :   'signed' | 'saturating' | field_mod ',' field_mod ;
length_bin_op   :   '+' | '-' | '*' | '<<' | '>>' ;
length_exp  :   const_value | field_name | length_exp length_bin_op length_exp | '(' length_exp ')' ;
bit_width   :   const_value | '*' ;
instance_declaration    :   header_instance | metadata_instance ;
header_instance :   scalar_instance | array_instance ;
scalar_instance :   'header' header_type_name instance_name ';' ;
array_instance  :   'header' header_type_name instance_name '[' const_value ']' ';' ;
instance_name   :   NAME ;

metadata_instance   :   'metadata' header_type_name instance_name ( metadata_initializer )? | ';' ;

metadata_initializer    :   '{' ( field_name ':' field_value ';' )+ '}' ;
header_ref  :   instance_name | instance_name '[' index ']' ;
index   :   const_value | 'last' ;
field_ref   :   header_ref '.' field_name ;
field_list_declaration  :   'field_list' field_list_name '{' ( field_list_entry ';')+ '}' ;
field_list_name :   NAME ;

field_list_entry    :   field_ref | header_ref | field_value | field_list_name | 'payload' ;
field_list_calculation_declaration :
    'field_list_calculation' field_list_calculation_name '{'
        'input' '{'
            ( field_list_name ';' )+
        '}'
        'algorithm' ':' stream_function_algorithm_name ';'
        'output_width' ':' const_value ';'
    '}'
    ;
field_list_calculation_name :   NAME ;
stream_function_algorithm_name  :   NAME ;
calculated_field_declaration    : 'calculated_field' field_ref '{' update_verify_spec+ '}' ;

update_verify_spec  :   update_or_verify field_list_calculation_name ( if_cond )? ';' ;
update_or_verify    :   'update' | 'verify' ;
if_cond :   'if' '(' calc_bool_cond ')' ;
calc_bool_cond :    'valid' '(' header_ref | field_ref ')' | field_ref '==' field_value ;
value_set_declaration : 'parser_value_set' value_set_name ';' ;
value_set_name  : NAME ;
parser_function_declaration :   'parser' parser_state_name '{' parser_function_body '}' ;
parser_state_name   :   NAME ;

parser_function_body : extract_or_set_statement* return_statement ;

extract_or_set_statement : extract_statement | set_statement ;
// TODO: This i guess is a bug, we'll see
extract_statement   : 'extract' '(' header_extract_ref ')' ';' ;
header_extract_ref  : instance_name | instance_name '[' header_extract_index ']' ;
header_extract_index    : const_value | 'next' ;
set_statement   :   'set_metadata' '(' field_ref',' metadata_expr ')' ';' ;
metadata_expr   :   field_value | field_or_data_ref ;

return_statement    :   return_value_type | 'return select' '(' select_exp ')' '{' case_entry+ '}' ;

return_value_type   :   'return' parser_state_name ';' | 'return' control_function_name ';'
    | 'parse_error' parser_exception_name ';' ;
control_function_name   :   NAME ;
parser_exception_name   :   NAME ;

case_entry :    value_list ':' case_return_value_type ;
value_list :    value_or_masked ( ',' value_or_masked )* | 'default' ;

case_return_value_type  : parser_state_name | control_function_name | 'parse_error' parser_exception_name ;

value_or_masked :   field_value | field_value 'mask' field_value | value_set_name ;

select_exp  :   field_or_data_ref (',' field_or_data_ref)* ;
field_or_data_ref   :   field_ref | 'latest.'field_name | 'current' '(' const_value ',' const_value ')' ;
parser_exception_declaration    :   'parser_exception' parser_exception_name '{'
    set_statement*
    return_or_drop ';'
    '}'
    ;

return_or_drop :    return_to_control | 'parser_drop' ;
return_to_control   :   'return' control_function_name ;
counter_declaration :   'counter' counter_name '{'
    'type' ':' counter_type ';'
    ( direct_or_static ';' )?
    ( 'instance_count' ':' const_expr ';' )?
    ( 'min_width' ':' const_expr ';' )?
    ( 'saturating' ';' )?
    '}'
    ;
    
counter_type    : 'bytes' | 'packets' | 'packets_and_bytes' ;
direct_or_static : direct_attribute | static_attribute ;
direct_attribute : 'direct' ':' table_name ;
static_attribute : 'static' ':' table_name ;
meter_declaration : 'meter' meter_name '{'
    'type' ':' meter_type ';'
    ( 'result' ':' field_ref ';' )?
    ( direct_or_static ';' )?
    (' instance_count' ':' const_expr ';' )?
    '}'
    ;

//TODO: const_expr in not defined, I'll leave this as a const_value.
const_expr  : const_value ;

table_name  :   NAME ;
meter_name  :   NAME ;
counter_name    :   NAME ;
register_name   :   NAME ;

meter_type : 'bytes' | 'packets' ;

register_declaration : 'register' register_name '{'
    width_declaration ';'
    ( direct_or_static ';' )?
    ( 'instance_count' ':' const_value ';' )?
    ( attribute_list ';' )?
    '}'
    ;

width_declaration : 'width' ':' const_value ';' ;
attribute_list : 'attributes' ':' attr_entry ;
attr_entry : 'signed' | 'saturating' | attr_entry ',' attr_entry ;
action_function_declaration : 'action' action_header '{' action_statement* '}' ;

action_header : action_name '(' ( param_list )? ')' ;
param_list : param_name (',' param_name)* ;
action_statement : action_name '(' ( arg (',' arg)* )? ')' ';' ;
arg : param_name | field_value | field_ref | header_ref ;

action_profile_declaration : 'action_profile' action_profile_name '{'
    action_specification
    ( 'size' ':' const_value ';' )?
    ( 'dynamic_action_selection' ':' selector_name ';' )?
    '}'
    ;

action_name : NAME ;
param_name  : NAME ;
selector_name   : NAME ;
action_profile_name : NAME ;

action_specification : 'actions' '{' ( action_name )+ '}' ;

action_selector_declaration : 'action_selector' selector_name '{'
    'selection_key' ':' field_list_calculation_name ';'
    '}'
    ;

table_declaration : 'table' table_name '{'
    ( 'reads' '{' field_match+ '}' )?
    table_actions
    ( 'min_size' ':' const_value ';' )?
    ( 'max_size' ':' const_value ';' )?
    ( 'size' ':' const_value ';' )?
    ( 'support_timeout' ':' ('true' | 'false') ';' )?
    '}'
    ;

field_match : field_or_masked_ref ':' field_match_type ';' ;
field_or_masked_ref : header_ref | field_ref | field_ref 'mask' const_value ;
field_match_type : 'exact' | 'ternary' | 'lpm' | 'range' | 'valid' ;
table_actions : action_specification | action_profile_specification ;
action_profile_specification : 'action_profile' ':' action_profile_name ;
control_function_declaration : 'control' control_fn_name control_block ;
control_fn_name : NAME;
control_block : '{' control_statement* '}' ;
control_statement : apply_table_call | apply_and_select_block | if_else_statement | control_fn_name '(' ')' ';' ;

apply_table_call : 'apply' '(' table_name ')' ';' ;

apply_and_select_block : 'apply' '(' table_name ')' '{' ( case_list )? '}' ;
case_list : action_case+ | hit_miss_case+ ;
action_case : action_or_default control_block ;
action_or_default : action_name | 'default' ;
hit_miss_case : hit_or_miss control_block ;
hit_or_miss : 'hit' | 'miss' ;

if_else_statement : 'if' '(' bool_expr ')' control_block ( else_block )? ;
else_block : 'else' control_block | 'else' if_else_statement ;
bool_expr : 'valid' '(' header_ref ')' | bool_expr bool_op bool_expr |
    'not' bool_expr | '(' bool_expr ')' | exp rel_op exp | 'true' | 'false' ;

exp : exp bin_op exp | un_op exp | field_ref | value | '(' exp ')' ;

//TODO: This is an assumption too
value   : const_value ;

bin_op : '+' | '*' | '-' | '<<' | '>>' | '&' | '|' | '^' ;
un_op : '~' | '-' ;
bool_op : 'or' | 'and' ;
rel_op : '>' | '>=' | '==' | '<=' | '<' | '!=' ;

NAME : [a-zA-Z][0-9a-zA-Z]* ;