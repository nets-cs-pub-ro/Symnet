in :: FromDevice()
out :: ToDevice()

filter :: IPClassifier(dst tcp port 80, -)
nat :: IPRewriter(pattern 141.85.241.245 15000 - - 0 1)

in -> filter -> nat -> out