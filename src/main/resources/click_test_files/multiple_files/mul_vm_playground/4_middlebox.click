if1_in :: FromDevice()
if1_out :: ToDevice()

if1_in -> EtherDecap() -> EtherEncap(2048, ffff.ffff.ffff, 0000.0000.0000) -> if1_out
