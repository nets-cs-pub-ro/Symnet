ifa_in :: FromDevice()
ifb_in :: FromDevice()
ifc_in :: FromDevice()
ifa_out :: ToDevice()
ifb_out :: ToDevice()
ifc_out :: ToDevice()

class :: IPClassifier(dst net 192.168.0.0/24, -)

ifa_in -> EtherDecap() -> EtherEncap(2048, 0000.0000.0000, ffff.ffff.ffff) -> ifc_out
ifb_in -> EtherDecap() -> EtherEncap(2048, 0000.0000.0000, ffff.ffff.ffff) -> ifc_out

ifc_in -> EtherDecap() -> class

class[0] -> EtherEncap(2048, 0000.0000.0000, aaaa.aaaa.aaaa) -> ifa_out
class[1] -> EtherEncap(2048, 0000.0000.0000, cccc.cccc.cccc) -> ifb_out
