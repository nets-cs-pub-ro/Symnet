package org.change.v2.abstractnet.neutron.elements

import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.expression.concrete.SymbolicValue
import org.change.v2.analysis.memory.Tag
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.instructions.Assign
import org.change.v2.analysis.processingmodels.instructions.Constrain
import org.change.v2.analysis.processingmodels.instructions.Fail
import org.change.v2.analysis.processingmodels.instructions.If
import org.openstack4j.model.network.ext.FirewallRule
import org.openstack4j.openstack.networking.domain.ext.NeutronFirewallRule.FirewallRuleAction
import org.openstack4j.model.compute.IPProtocol

class NetFirewallRule(rule : FirewallRule) extends NetAbsElement {
  
  private def hasSrcIp = org.change.v2.helpers.Utilss.isValidIpv4(rule.getSourceIpAddress)
  private def hasDstIp = org.change.v2.helpers.Utilss.isValidIpv4(rule.getDestinationIpAddress)
  private def hasSrcPort = org.change.v2.helpers.Utilss.isValidPort(rule.getSourcePort)
  private def hasDstPort = org.change.v2.helpers.Utilss.isValidPort(rule.getDestinationPort)
  private def action = 
    if (rule.getAction != FirewallRuleAction.ALLOW) Fail("DENY on rule " + rule.getName) 
    else InstructionBlock(Allocate("TrafficAllowed"), Assign("TrafficAllowed", ConstantValue(1)))
  
  private def symnetSrcIp = {
    if (hasSrcIp) 
      IpFilter.filterIp("IPSrc", rule.getSourceIpAddress)
    else null
  }
  private def symnetDstIp = {
    if (hasDstIp) 
      IpFilter.filterIp("IPDst", rule.getDestinationIpAddress)
    else null
  }
  private def symnetDstPort = {
    if (hasDstPort) 
      IpFilter.filterPort("DstPort", rule.getDestinationPort)
    else null
  }
  private def symnetSrcPort = {
    if (hasSrcPort) 
      IpFilter.filterPort("SrcPort", rule.getDestinationPort)
    else null
  }
  
  
  
  
  def symnetCode() : Instruction = {
    val someVal = List[Instruction](symnetSrcIp, 
        symnetDstIp, symnetDstPort, symnetSrcPort).filter { x => x != null }
    val ipProto = rule.getProtocol
    val actual = {
      if (ipProto.value().equals(IPProtocol.TCP.value())) {
        6
      } else if (ipProto.value().equals(IPProtocol.UDP.value())) {
        17
      } else if (ipProto.value().equals(IPProtocol.ICMP.value())) {
        1
      } else {
        throw new IllegalArgumentException("Wrong protocol " + ipProto);
      }
    }
    val constrainAlreadyAllowed = Constrain("TrafficAllowed", :==:(ConstantValue(1)))
    
    val constrainIpProto = Constrain(Tag("IPProto"), :==:(ConstantValue(actual)));
    val fullBlock = If(constrainAlreadyAllowed,
      NoOp,
      If(constrainIpProto, 
        IpFilter.andInstrs(someVal, action, NoOp), 
        NoOp));
    fullBlock
  }
}

object IpFilter {
  def filterIp(tag : String, ip : String) = {
    val (ipAddr, ipMask) = IpHelpers.parseIpAddress(ip)
    val (rangeStart, rangeEnd) = ipAndMaskToInterval(ipAddr, ipMask)
    Constrain(Tag(tag), :&:(:<:(ConstantValue(rangeEnd)), :>:(ConstantValue(rangeStart))))
  }
  
  def filterPort(tag : String, port : String) = {
    Constrain(Tag(tag), :==:(ConstantValue(Integer.parseInt(port))))
  }
  
  def andInstrs(instrs : List[Instruction], thenInstr : Instruction, elseInstr : Instruction) : Instruction = {
    if (instrs.isEmpty) 
      thenInstr
    else {
      val left = instrs.splitAt(1)._2;
      val constraint = instrs(0);
      If (constraint, andInstrs(left, thenInstr, elseInstr), elseInstr)
    }
  }
  
}