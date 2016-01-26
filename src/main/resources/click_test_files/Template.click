FromDevice -> r :: IPRewriter(keep 0 1, drop)[1] -> dst :: ToDevice

r[0] -> IPMirror() -> [1]r;

r[2] -> Discard;
