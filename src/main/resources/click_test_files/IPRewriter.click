src :: FromDevice
dst :: ToDevice

rw :: IPRewriter(keep 0 1)

src -> rw    -> dst
       rw[1] -> dst