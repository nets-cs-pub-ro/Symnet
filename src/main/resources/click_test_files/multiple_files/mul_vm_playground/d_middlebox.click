ifa_in :: FromDevice()
ifa_out :: ToDevice()

ifa_in -> EtherDecap() -> EtherEncap(2048, ffff.ffff.ffff, 0000.0000.0000) -> ifa_out
