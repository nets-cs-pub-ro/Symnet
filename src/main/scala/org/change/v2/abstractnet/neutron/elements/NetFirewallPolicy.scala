package org.change.v2.abstractnet.neutron.elements

import org.change.v2.abstractnet.neutron.NeutronWrapper
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions.InstructionBlock
import org.openstack4j.model.network.ext.{FirewallPolicy, FirewallRule}

import scala.collection.JavaConverters.iterableAsScalaIterableConverter

class NetFirewallPolicy(policy : FirewallPolicy, wrapper : NeutronWrapper) extends NetAbsElement {
  def firewallRules() : List[FirewallRule] = {
    wrapper.getRules(policy.getFirewallRuleIds).asScala.toList
  }
  
  def symnetCode() : Instruction = {
      InstructionBlock(firewallRules.map { x => new NetFirewallRule(x).symnetCode() })
  }

}