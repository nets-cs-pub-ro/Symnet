ifa_in  :: FromDevice()
ifa_out :: ToDevice()

ifa_in -> EtherDecap() -> IPMirror() -> EtherEncap(2048,aaaa.aaaa.aaaa,0000.0000.0000) -> ifa_out

