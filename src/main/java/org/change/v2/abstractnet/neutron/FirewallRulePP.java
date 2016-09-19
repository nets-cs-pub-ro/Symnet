package org.change.v2.abstractnet.neutron;

import java.net.InetAddress;

import org.openstack4j.model.network.ext.FirewallRule;

public class FirewallRulePP {
	private FirewallRule fRule;
	private Long srcPort, dstPort, srcAddr, dstAddr;
	

	public FirewallRulePP(FirewallRule fRule) {
		super();
		if (fRule == null)
			throw new IllegalArgumentException();
		this.fRule = fRule;
	}
	
	public boolean hasSrcPort() {
		try  {
			if (srcPort == null)
				srcPort = Long.parseLong(fRule.getSourcePort());
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	public boolean hasDstPort() {
		try  {
			if (dstPort == null)
				dstPort = Long.parseLong(fRule.getDestinationPort());
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	
	
}
