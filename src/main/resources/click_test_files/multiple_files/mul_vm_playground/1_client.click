if1_in :: FromDevice()
if2_in :: FromDevice()
if1_out :: ToDevice()
if2_out :: ToDevice()

if1_in -> EtherEncap(2048, cccc.cccc.cccc, 0000.0000.0000) -> if2_out
if2_in -> if1_out
