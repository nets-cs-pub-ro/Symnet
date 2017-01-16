in :: FromDevice() -> switch :: MPLSSwitch(1,2)

internet :: ToDevice
to-core-2 :: ToDevice

switch[0] -> internet
switch[1] -> to-core-2