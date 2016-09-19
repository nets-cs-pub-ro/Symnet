package org.change.v2.abstractnet.neutron;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.network.ext.Firewall;
import org.openstack4j.model.network.ext.FirewallPolicy;
import org.openstack4j.model.network.ext.FirewallRule;

public class NeutronWrapper extends Creator {
	private OSClient os;
	public OSClient getOs() {
		return os;
	}

	private Map<String, FirewallPolicy> policies = new HashMap<String, FirewallPolicy>();
	private Map<String, FirewallRule> rules = new HashMap<String, FirewallRule>();

	public NeutronWrapper(String host, String userName, String password, String project) {
		super(org.openstack4j.openstack.OSFactory.builder()
                .endpoint(host)
                .credentials(userName, password)
                .tenantName(project)
                .authenticate());
		this.os = super.getOsClient();		
	}
	public NeutronWrapper(String host, String userName, String password) {
		this(host, userName, password, userName + "_prj");
	}
	
	public List<? extends Firewall> getFirewalls() {
		return os.networking().firewalls().firewall().list();
	}
	
	public FirewallPolicy getPolicy(String name) {
		FirewallPolicy policy = null;
		if (!policies.containsKey(name)) {
			 policy = os.networking().firewalls().firewallpolicy().get(name);
			 this.policies.put(name, policy);
		} else {
			policy = this.policies.get(name);
		}
		return policy;
	}
	
	public FirewallRule getRule(String name) {
		FirewallRule rule = null;
		if (!rules.containsKey(name)) {
			rule = os.networking().firewalls().firewallrule().get(name);
			this.rules.put(name, rule);
		} else {
			rule = this.rules.get(name);
		}
		return rule;
	}
	
	public Iterable<FirewallRule> getRules(final Iterable<String> policy) {
		return new Iterable<FirewallRule> () {

			@Override
			public Iterator<FirewallRule> iterator() {
				final Iterator<String> theIterator = policy.iterator();
				return new Iterator<FirewallRule>() {

					@Override
					public boolean hasNext() {
						return theIterator.hasNext();
					}

					@Override
					public FirewallRule next() {
						return getRule(theIterator.next());
					}
					
				};
			}
			
		};
		
	}
	
	
	
}
