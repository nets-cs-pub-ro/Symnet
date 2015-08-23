
global_nat :: IPRewriter(keep 0 1,pattern 141.85.225.202 60000-65535 - - 2 3,keep 2 4);

dest_cl :: IPClassifier(dst net 172.16.2.254 mask 255.255.255.0,-);

dest_clp :: IPClassifier(dst net 172.16.2.254 mask 255.255.255.0,-);

unstatic_cl :: IPClassifier(-);

unstatic_rw :: IPRewriter(keep 0 1);

pn_1 :: Paint(1);
pn_2 :: Paint(2);
pn_3 :: Paint(3);
pn_0 :: Paint(4);

main_input :: FromDevice(eth1,SNIFFER true,OUTBOUND false);

main_tee :: Tee(1);

out_PROF_CS :: Null;

in_PROF_CS :: Null;

pn_PROF_CS :: Paint(80);

isTCP1 :: IPClassifier(tcp,udp,-);

nat_cl_PROF_CS :: IPClassifier(src net 172.16.2.0 mask 255.255.255.0,-);

ps_PROF_CS :: PaintSwitch();

just_pass_paint :: Paint(100);

outside :: Null;

main_output :: FromDevice(eth2,SNIFFER true,OUTBOUND false);

isTCP2 :: IPClassifier(tcp,udp,-);

ps :: PaintSwitch();

cl_incoming :: IPClassifier(dst net 141.85.228.0 mask 255.255.255.192,-);

	global_nat[0]->[0]pn_0;
		pn_0[0]->[0]unstatic_cl;
			unstatic_cl[0]->[0]unstatic_rw;
				unstatic_rw[0]->[0]ps;
					ps[0]->[0]cl_incoming;
						cl_incoming[0]->[0]dest_clp;
							dest_clp[0]->[0]out_PROF_CS;
								out_PROF_CS[0]->[0]Discard;
							dest_clp[1]->[0]Discard;
						cl_incoming[1]->[0]Discard;
					ps[1]->[0]dest_clp;
					ps[2]->[0]dest_clp;
					ps[3]->[0]cl_incoming;
				unstatic_rw[1]->[0]Discard;
	global_nat[1]->[0]just_pass_paint;
		just_pass_paint[0]->[0]dest_cl;
			dest_cl[0]->[0]ps_PROF_CS;
				ps_PROF_CS[0]->[0]Discard;
				ps_PROF_CS[1]->[0]Discard;
				ps_PROF_CS[2]->[0]Discard;
				ps_PROF_CS[3]->[0]Discard;
				ps_PROF_CS[4]->[0]Discard;
				ps_PROF_CS[5]->[0]Discard;
				ps_PROF_CS[6]->[0]Discard;
				ps_PROF_CS[7]->[0]Discard;
				ps_PROF_CS[8]->[0]Discard;
				ps_PROF_CS[9]->[0]Discard;
				ps_PROF_CS[10]->[0]Discard;
				ps_PROF_CS[11]->[0]Discard;
				ps_PROF_CS[12]->[0]Discard;
				ps_PROF_CS[13]->[0]Discard;
				ps_PROF_CS[14]->[0]Discard;
				ps_PROF_CS[15]->[0]Discard;
				ps_PROF_CS[17]->[0]Discard;
				ps_PROF_CS[16]->[0]Discard;
				ps_PROF_CS[19]->[0]Discard;
				ps_PROF_CS[18]->[0]Discard;
				ps_PROF_CS[21]->[0]Discard;
				ps_PROF_CS[20]->[0]Discard;
				ps_PROF_CS[23]->[0]Discard;
				ps_PROF_CS[22]->[0]Discard;
				ps_PROF_CS[25]->[0]Discard;
				ps_PROF_CS[24]->[0]Discard;
				ps_PROF_CS[27]->[0]Discard;
				ps_PROF_CS[26]->[0]Discard;
				ps_PROF_CS[29]->[0]Discard;
				ps_PROF_CS[28]->[0]Discard;
				ps_PROF_CS[31]->[0]Discard;
				ps_PROF_CS[30]->[0]Discard;
				ps_PROF_CS[34]->[0]Discard;
				ps_PROF_CS[35]->[0]Discard;
				ps_PROF_CS[32]->[0]Discard;
				ps_PROF_CS[33]->[0]Discard;
				ps_PROF_CS[38]->[0]Discard;
				ps_PROF_CS[39]->[0]Discard;
				ps_PROF_CS[36]->[0]Discard;
				ps_PROF_CS[37]->[0]Discard;
				ps_PROF_CS[42]->[0]Discard;
				ps_PROF_CS[43]->[0]Discard;
				ps_PROF_CS[40]->[0]Discard;
				ps_PROF_CS[41]->[0]Discard;
				ps_PROF_CS[46]->[0]Discard;
				ps_PROF_CS[47]->[0]Discard;
				ps_PROF_CS[44]->[0]Discard;
				ps_PROF_CS[45]->[0]Discard;
				ps_PROF_CS[51]->[0]Discard;
				ps_PROF_CS[50]->[0]Discard;
				ps_PROF_CS[49]->[0]Discard;
				ps_PROF_CS[48]->[0]Discard;
				ps_PROF_CS[55]->[0]Discard;
				ps_PROF_CS[54]->[0]Discard;
				ps_PROF_CS[53]->[0]Discard;
				ps_PROF_CS[52]->[0]Discard;
				ps_PROF_CS[59]->[0]Discard;
				ps_PROF_CS[58]->[0]Discard;
				ps_PROF_CS[57]->[0]Discard;
				ps_PROF_CS[56]->[0]Discard;
				ps_PROF_CS[63]->[0]Discard;
				ps_PROF_CS[62]->[0]Discard;
				ps_PROF_CS[61]->[0]Discard;
				ps_PROF_CS[60]->[0]Discard;
				ps_PROF_CS[68]->[0]Discard;
				ps_PROF_CS[69]->[0]Discard;
				ps_PROF_CS[70]->[0]Discard;
				ps_PROF_CS[71]->[0]Discard;
				ps_PROF_CS[64]->[0]Discard;
				ps_PROF_CS[65]->[0]Discard;
				ps_PROF_CS[66]->[0]Discard;
				ps_PROF_CS[67]->[0]Discard;
				ps_PROF_CS[76]->[0]Discard;
				ps_PROF_CS[77]->[0]Discard;
				ps_PROF_CS[78]->[0]Discard;
				ps_PROF_CS[79]->[0]Discard;
				ps_PROF_CS[72]->[0]Discard;
				ps_PROF_CS[73]->[0]Discard;
				ps_PROF_CS[74]->[0]Discard;
				ps_PROF_CS[75]->[0]Discard;
				ps_PROF_CS[80]->[0]out_PROF_CS;
			dest_cl[1]->[0]Discard;
	global_nat[2]->[0]dest_cl;
	global_nat[3]->[0]pn_2;
		pn_2[0]->[0]unstatic_cl;
	global_nat[4]->[0]pn_1;
		pn_1[0]->[0]unstatic_cl;
	pn_3[0]->[0]unstatic_cl;
	main_input[0]->[0]main_tee;
		main_tee[0]->[0]in_PROF_CS;
			in_PROF_CS[0]->[0]pn_PROF_CS;
				pn_PROF_CS[0]->[0]isTCP1;
					isTCP1[0]->[0]nat_cl_PROF_CS;
					isTCP1[1]->[0]nat_cl_PROF_CS;
						nat_cl_PROF_CS[0]->[1]global_nat;
						nat_cl_PROF_CS[1]->[2]global_nat;
					isTCP1[2]->[0]dest_cl;
	outside[0]->[0]isTCP2;
		isTCP2[0]->[0]global_nat;
		isTCP2[1]->[0]global_nat;
		isTCP2[2]->[0]pn_3;
	main_output[0]->[0]outside;

