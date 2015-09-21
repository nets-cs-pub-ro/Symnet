
global_nat :: IPRewriter(keep 0 1, pattern 141.85.225.204 60000-65535 - - 2 3, keep 2 4);

dest_cl :: IPClassifier(dst net 172.16.4.254/10,-);

dest_clp :: IPClassifier(dst net 172.16.4.254/10,-);

unstatic_cl :: IPClassifier(-);

unstatic_rw :: IPRewriter(keep 0 1);

pn_1 :: Paint(1);
pn_2 :: Paint(2);
pn_3 :: Paint(3);
pn_0 :: Paint(0);
main_input :: FromDevice(eth1, SNIFFER true, OUTBOUND false);
main_tee :: Tee(1);
out_LABS_CS :: Null;
in_LABS_CS :: Null;
pn_LABS_CS :: Paint(5);

isTCP1 :: IPClassifier(tcp or udp,-);

nat_cl_LABS_CS :: IPClassifier(src net 172.16.4.0/10,-);

ps_LABS_CS::PaintSwitch();

just_pass_paint::Paint(100);

outside::Null;

main_output::FromDevice(eth2,SNIFFER true,OUTBOUND false);

isTCP2::IPClassifier(tcp or udp,-);

ps::PaintSwitch();

cl_incoming::IPClassifier(tcp dst host 141.85.225.151 and tcp dst port 21 or
tcp dst host 141.85.225.151 and tcp dst port 80 or 
tcp dst host 141.85.225.151 and tcp dst port 5000 or 
tcp dst host 141.85.225.151 and tcp dst port 5001 or 
tcp dst host 141.85.225.151 and tcp dst port 443 or 
tcp dst host 141.85.225.152 and tcp dst port 22 or 
tcp dst host 141.85.225.153,-);

	global_nat[0]->[0]pn_0;
		pn_0[0]->[0]unstatic_cl;
			unstatic_cl[0]->[0]unstatic_rw;
				unstatic_rw[0]->[0]ps;
					ps[0]->[0]cl_incoming;
						cl_incoming[0]->[0]dest_clp;
							dest_clp[0]->[0]out_LABS_CS;
								out_LABS_CS[0]->[0]Discard;
							dest_clp[1]->[0]Discard;
						cl_incoming[1]->[0]Discard;
					ps[1]->[0]dest_clp;
					ps[2]->[0]dest_clp;
					ps[3]->[0]cl_incoming;
				unstatic_rw[1]->[0]Discard;
	global_nat[1]->[0]just_pass_paint;
		just_pass_paint[0]->[0]dest_cl;
			dest_cl[0]->[0]ps_LABS_CS;
				ps_LABS_CS[0]->[0]Discard;
				ps_LABS_CS[1]->[0]Discard;
				ps_LABS_CS[2]->[0]Discard;
				ps_LABS_CS[3]->[0]Discard;
				ps_LABS_CS[4]->[0]Discard;
				ps_LABS_CS[5]->[0]out_LABS_CS;
			dest_cl[1]->[0]Discard;
	global_nat[2]->[0]dest_cl;
	global_nat[3]->[0]pn_2;
		pn_2[0]->[0]unstatic_cl;
	global_nat[4]->[0]pn_1;
		pn_1[0]->[0]unstatic_cl;
	pn_3[0]->[0]unstatic_cl;
	main_input[0]->[0]main_tee;
		main_tee[0]->[0]in_LABS_CS;
			in_LABS_CS[0]->[0]pn_LABS_CS;
				pn_LABS_CS[0]->[0]isTCP1;
					isTCP1[0]->[0]nat_cl_LABS_CS;
						nat_cl_LABS_CS[0]->[1]global_nat;
						nat_cl_LABS_CS[1]->[2]global_nat;
					isTCP1[1]->[0]dest_cl;
	outside[0]->[0]isTCP2;
		isTCP2[0]->[0]global_nat;
		isTCP2[1]->[0]pn_3;
	main_output[0]->[0]outside;
