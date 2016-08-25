in :: FromDevice()
out :: ToDevice()

tunnel_in :: IPEncap(94, 18.26.4.24, 140.247.60.147)

tunnel_out :: StripIPHeader()

in -> tunnel_in -> tunnel_out -> out
