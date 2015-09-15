src :: FromDevice
dst :: ToDevice

rw :: IPRewriter(pattern 0.0.0.1 1 0.0.0.2 2 0 1)

src -> rw    -> dst
       rw[1] -> dst