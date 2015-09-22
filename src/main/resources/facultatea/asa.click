main_input :: FromDevice(eth1, SNIFFER true, OUTBOUND false);

main_tee :: Tee(1);

in_LABS_CS :: Null;

pn_LABS_CS :: Paint(5);

isTCP1 :: IPClassifier(tcp, udp,-);

main_input[0] -> VLANDecap() -> EtherDecap() -> main_tee[0] -> [0]in_LABS_CS -> pn_LABS_CS;
                                                 pn_LABS_CS -> isTCP1;

nat_cl_LABS_CS :: IPClassifier(src net 172.16.4.0/10,-);

isTCP1[0] -> nat_cl_LABS_CS;
isTCP1[1] -> nat_cl_LABS_CS;
isTCP1[2] -> dest_cl;

    global_nat :: IPRewriter(keep 0 1, pattern 141.85.225.204 60000-65535 - - 2 3, keep 2 4);

nat_cl_LABS_CS[0] -> [1]global_nat;
nat_cl_LABS_CS[1] -> [2]global_nat;

dest_cl :: IPClassifier(dst net 172.16.4.254/10,-);

global_nat[2]->[0]dest_cl;

ps_LABS_CS :: IPClassifier(paint color 5, -);

dest_cl -> ps_LABS_CS;
           ps_LABS_CS[1] -> Discard;

out_LABS_CS :: Null;
out_LABS_CS_EXIT :: Discard;
            ps_LABS_CS[0] -> out_LABS_CS -> out_LABS_CS_EXIT;

dest_cl[1] -> EtherEncap(2048, 0.0.1, 0023.ebbb.f14c) -> VLANEncap(225) -> to_internet :: ToDevice();

main_tee[1] -> ToDevice()

main_output :: FromDevice(eth2,SNIFFER true,OUTBOUND false);
outside :: Null;
isTCP2 :: IPClassifier(tcp, udp,-);

main_output -> outside -> isTCP2;

isTCP2 -> global_nat;
isTCP2[1] -> global_nat;

unstatic_cl :: Null;
pn_0 :: Paint(0);

global_nat -> pn_0 -> unstatic_cl;

pn_3 :: Paint(3);
isTCP2[2] -> pn_3;

ps :: IPClassifier(paint color 0, paint color 1, paint color 2, paint color 3);

unstatic_rw :: IPRewriter(keep 0 1);
pn_3 -> unstatic_cl -> unstatic_rw -> ps;
unstatic_rw[1] -> Discard;

cl_incoming :: IPClassifier(tcp && dst host 141.85.225.151 && dst tcp port 21, tcp && dst host 141.85.225.151 && dst tcp port 80, tcp && dst host 141.85.225.151 && dst tcp port 5000, tcp && dst host 141.85.225.151 && dst tcp port 5001, tcp && dst host 141.85.225.151 && dst tcp port 443, tcp && dst host 141.85.225.152 && dst tcp port 22, tcp && dst host 141.85.225.153, -);

ps -> cl_incoming;

dest_clp :: IPClassifier(dst net 172.16.4.254/10,-);

cl_incoming[0] -> [0]dest_clp;
cl_incoming[1] -> [0]dest_clp;
cl_incoming[2] -> [0]dest_clp;
cl_incoming[3] -> [0]dest_clp;
cl_incoming[4] -> [0]dest_clp;
cl_incoming[5] -> [0]dest_clp;
cl_incoming[6] -> [0]dest_clp;

via_dest_clp :: Discard
dest_clp -> out_LABS_CS -> via_dest_clp;

dest_clp[1] -> Discard;
cl_incoming[7] -> Discard;

ps[1] -> dest_clp;
ps[2] -> dest_clp;
ps[3] -> cl_incoming;

just_pass_paint :: Paint(100);

global_nat[1] -> just_pass_paint -> dest_cl;

pn_1 :: Paint(1);
pn_2 :: Paint(2);

global_nat[3] -> pn_2 -> unstatic_cl;
global_nat[4] -> pn_1 -> unstatic_cl;