ifa_in :: FromDevice()
ifb_in :: FromDevice()
ifa_out :: ToDevice()
ifb_out :: ToDevice()

ifa_in -> EtherEncap(2048, cccc.cccc.cccc, 0000.0000.0000) -> DHCPSetState() -> ifb_out
ifb_in -> ifa_out
