ifa_in :: FromDevice()
ifa_out :: ToDevice()
ifb_in :: FromDevice()
ifb_out :: ToDevice()
class :: IPClassifier(dst net 192.168.0.0/24, -)

ifa_in -> class
ifb_in -> class

class[0] -> ifa_out
class[1] -> ifb_out