ifa_in  :: FromDevice()
ifa_out :: ToDevice()

ifa_in -> VLANDecap() -> EtherDecap() -> DHCPCheckState() -> IPMirror() -> EtherEncap(2048,aaaa.aaaa.aaaa,0000.0000.0000) -> ifa_out
