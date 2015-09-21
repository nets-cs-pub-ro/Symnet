if1_in  :: FromDevice()
if1_out :: ToDevice()

if1_in -> EtherDecap() -> IPMirror() -> EtherEncap(2048,aaaa.aaaa.aaaa,0000.0000.0000) -> if1_out

