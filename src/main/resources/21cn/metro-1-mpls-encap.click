in :: FromDevice() -> lb :: IPClassifier(src net 192.168.1.0/24, src net 192.168.2.0/24)

exit :: ToDevice

lb[0] -> MPLSEncap(1) -> exit
lb[1] -> MPLSEncap(2) -> exit