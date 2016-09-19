package org.change.v2.helpers;

public class Utilss {
	public static boolean isValidIpv4(String addr) {
		if (addr == null || addr.length() == 0) {
			return false;
		}
		String[] split = addr.split("\\.");
		if (split.length != 4) 
			return false;
		try {
			for (int i = 0; i < split.length; i++) {
				if (i == split.length - 1) {
					if (split[i].contains("/")) {
						split[i] = split[i].substring(0, split[i].indexOf('/'));
					}
				}
				Integer in = Integer.parseInt(split[i]);
				if (in == null || in < 0 || in > 255) {
						return false;
				}
			}
 		} catch (NumberFormatException nfe) {
 			return false;
 		}
		return true;
	}
	
	public static boolean isValidPort(String portRepr) {
		if (portRepr == null || portRepr.length() == 0) {
			return false;
		}
		try {
			Integer in = Integer.parseInt(portRepr);
 		} catch (NumberFormatException nfe) {
 			return false;
 		}
		return true;
	}
}
