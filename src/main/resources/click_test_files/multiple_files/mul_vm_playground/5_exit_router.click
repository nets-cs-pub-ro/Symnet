if1_in  :: FromDevice()
if1_out :: ToDevice()

if1_in -> EtherDecap() -> IPMirror() -> EtherEncap(2048, aa.aa.aa.aa.aa.aa, 00.00.00.00.00.00) -> if1_out
