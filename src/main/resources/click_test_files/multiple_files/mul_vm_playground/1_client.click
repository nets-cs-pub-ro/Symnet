if1_in :: FromDevice()
if2_in :: FromDevice()
if1_out :: ToDevice()
if2_out :: ToDevice()

if1_in -> EtherDecap() -> EtherEncap(2048, cc.cc.cc.cc.cc.cc, 00.00.00.00.00.00) -> if2_out
if2_in -> if1_out
