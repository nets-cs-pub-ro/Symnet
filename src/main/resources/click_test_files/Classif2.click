src :: FromDevice
dst :: ToDevice

cl_incoming :: IPClassifier(dst net 0.0.0.0/8 and src host 0.0.0.1, false)

src -> cl_incoming[0] -> dst

cl_incoming[2] -> dst