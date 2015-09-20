if1_in :: FromDevice()
if1_out :: ToDevice()

if1_in -> EtherDecap() -> EtherEncap(2048, ff.ff.ff.ff.ff.ff, 00.00.00.00.00.00) -> if1_out
