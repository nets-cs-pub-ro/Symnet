ifa_in :: FromDevice()
ifb_in :: FromDevice()
ifc_in :: FromDevice()
ifa_out :: ToDevice()
ifb_out :: ToDevice()
ifc_out :: ToDevice()

class :: IPClassifier(dst net 192.168.0.0/24, -)

ifa_in -> EtherDecap() -> IPEncap(94, 10.0.0.1, 10.0.0.10) -> EtherEncap(2048, 0000.0000.0000, ffff.ffff.ffff) -> ClampMTU(1536) -> ifc_out
ifb_in -> EtherDecap() -> IPEncap(94, 10.0.0.1, 10.0.0.10) -> EtherEncap(2048, 0000.0000.0000, ffff.ffff.ffff) -> ClampMTU(1536) -> ifc_out

ifc_in -> EtherDecap() -> IPDecap(94) -> class

class[0] -> EtherEncap(2048, 0000.0000.0000, aaaa.aaaa.aaaa) -> ClampMTU(1536) -> ifa_out
class[1] -> EtherEncap(2048, 0000.0000.0000, cccc.cccc.cccc) -> ClampMTU(1536) -> ifb_out
