if1_in :: FromDevice()
if2_in :: FromDevice()
if3_in :: FromDevice()
if1_out :: ToDevice()
if2_out :: ToDevice()
if3_out :: ToDevice()

class :: IPClassifier(dst net 192.168.0.0/24, dst net 0.0.0.0/0, -)

if1_in -> EtherDecap() -> EtherEncap(2048, 00.00.00.00.00.00, ff.ff.ff.ff.ff.ff) -> if3_out
if2_in -> EtherDecap() -> EtherEncap(2048, 00.00.00.00.00.00, ff.ff.ff.ff.ff.ff) -> if3_out

if3_in -> class

class[0] -> EtherDecap() -> EtherEncap(2048, 00.00.00.00.00.00, aa.aa.aa.aa.aa.aa) -> if1_out
class[1] -> EtherDecap() -> EtherEncap(2048, 00.00.00.00.00.00, cc.cc.cc.cc.cc.cc) -> if2_out
class[2] -> Discard()
