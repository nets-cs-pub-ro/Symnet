package org.change.v2.abstractnet.neutron.elements

import org.change.v2.abstractnet.neutron.NeutronWrapper
import org.openstack4j.model.network.ext.Firewall
import org.change.v2.analysis.processingmodels.Instruction

class NetFirewall(fWall : Firewall, wrapper : NeutronWrapper) extends NetAbsElement {
  def symnetCode() : Instruction = {
    new NetFirewallPolicy(wrapper.getPolicy(fWall.getPolicy), wrapper).symnetCode()
  }
}