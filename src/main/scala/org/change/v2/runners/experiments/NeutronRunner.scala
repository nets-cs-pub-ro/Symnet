package org.change.v2.runners.experiments
import java.io.{File, FileOutputStream, PrintStream}

import org.change.v2.abstractnet.neutron.NeutronWrapper
import org.change.v2.abstractnet.neutron.elements.{NetAbsElement, NetFirewallPolicy, NetFirewallRule, NetRouter}
import org.change.v2.abstractnet.neutron.elements.NeutronHelper._
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.memory.{State, Tag}
import org.change.v2.analysis.memory.TagExp.IntImprovements
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions.{Allocate, Assign, CreateTag, InstructionBlock}
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.change.v2.util.conversion.RepresentationConversion._
import org.openstack4j.model.network.ext.{Firewall, FirewallPolicy, FirewallRule}
import org.openstack4j.openstack.networking.domain.NeutronRouter

import scala.collection.JavaConversions._

object NeutronRunner {
  def main(argv : Array[String]) {
    val (apiAddr, userName, password, project) = readCredentials()
    
    val wrapper = new NeutronWrapper(apiAddr,
          userName,
          password,
          project)
    val fwalls : List[Firewall] = null;
    val listOfFwalls = wrapper.getFirewalls;
    val it = listOfFwalls.iterator
    val setOfRules = List[FirewallRule]();
    // test policies
//    while (it.hasNext) {
//      val x = it.next;
//      val policy = wrapper.getPolicy(x.getPolicy);
//      testPolicy(policy, wrapper)
//    }

    // test routers
     val r = wrapper.getOs.networking.router.list
     r.foreach { x => testRouter(x.asInstanceOf[NeutronRouter], wrapper) }
  }
  
    
  def testRouter(router : NeutronRouter, wrapper : NeutronWrapper) = {
    val output = new PrintStream(new FileOutputStream(new File("neutron_router_" + router.getName + ".output")))
     output.println(router.getName)
     val (successful, failed) = genericTest(NetRouter(wrapper, router))(State.clean, true);
    output.println(
      successful.map(_.jsonString).mkString("Successful: {\n", "\n", "}\n") +
        failed.map(_.jsonString).mkString("Failed: {\n", "\n", "}\n")
    )

    output.close()
  }
  
  def testPolicy(policy : FirewallPolicy, wrapper : NeutronWrapper) = {
     val output = new PrintStream(new FileOutputStream(new File("neutron_policy_" + policy.getName + ".output")))
     output.println(policy.getName)
     val (successful, failed) = genericTest(new NetFirewallPolicy(policy, wrapper).symnetCode())(State.clean, true);
    output.println(
      successful.map(_.jsonString).mkString("Successful: {\n", "\n", "}\n") +
        failed.map(_.jsonString).mkString("Failed: {\n", "\n", "}\n")
    )

    output.close()
  }
  
  def testRule(y : FirewallRule) = {
    val output = new PrintStream(new FileOutputStream(new File("neutron_rule_" + y.getName + ".output")))
    val (successful, failed) = genericEx(y)(State.clean, true)
    output.println(
      successful.map(_.jsonString).mkString("Successful: {\n", "\n", "}\n") +
        failed.map(_.jsonString).mkString("Failed: {\n", "\n", "}\n")
    )

    output.close()
  }
  
  def genericTest(instr : Instruction) = {
    InstructionBlock(// At address 0 the L3 header starts
      CreateTag("L3HeaderStart", 0),
      CreateTag("L4HeaderStart", Tag("L3HeaderStart") + 160),
      // Also mark IP Src and IP Dst fields and allocate memory
      CreateTag("IPSrc", Tag("L3HeaderStart") + 96),
      // For raw memory access (via tags or ints), space has to be allocated beforehand.
      Allocate(Tag("IPSrc"), 32),
      CreateTag("IPDst", Tag("L3HeaderStart") + 128),
      Allocate(Tag("IPDst"), 32),
      CreateTag("IPProto", Tag("L3HeaderStart") + 72),
      Allocate(Tag("IPProto"), 8),
      Assign(Tag("IPProto"), SymbolicValue()),
      //Initialize IPSrc and IPDst
      Assign(Tag("IPSrc"), SymbolicValue()),
      Assign(Tag("IPDst"), SymbolicValue()),
      // here comes the TCP part
      CreateTag("SrcPort", Tag("L4HeaderStart") + 0),
      Allocate(Tag("SrcPort"), 16),
      CreateTag("DstPort", Tag("L4HeaderStart") + 16),
      Allocate(Tag("DstPort"), 16),
      Assign(Tag("DstPort"), SymbolicValue()),
      Assign(Tag("SrcPort"), SymbolicValue()),
      instr)
  }
  
  def genericEx(rule : FirewallRule) = {
    genericTest(new NetFirewallRule(rule).symnetCode())
  }
  
  def genericAbsTest(elem : NetAbsElement) = {
    genericTest(elem.symnetCode())
  }
  
  def genericExIp(rule : FirewallRule, srcIpAddr : String,
      dstIpAddr : String) : Instruction = {
    InstructionBlock(// At address 0 the L3 header starts
      CreateTag("L3HeaderStart", 0),
      CreateTag("L4HeaderStart", Tag("L3HeaderStart") + 160),
      // Also mark IP Src and IP Dst fields and allocate memory
      CreateTag("IPSrc", Tag("L3HeaderStart") + 96),
      // For raw memory access (via tags or ints), space has to be allocated beforehand.
      Allocate(Tag("IPSrc"), 32),
      CreateTag("IPDst", Tag("L3HeaderStart") + 128),
      Allocate(Tag("IPDst"), 32),
      CreateTag("IPProto", Tag("L3HeaderStart") + 72),
      Allocate(Tag("IPProto"), 8),
      Assign(Tag("IPProto"), ConstantValue(6)),
      //Initialize IPSrc and IPDst
      Assign(Tag("IPSrc"), ConstantValue(ipToNumber(srcIpAddr))),
      Assign(Tag("IPDst"), ConstantValue(ipToNumber(dstIpAddr))),
      // here comes the TCP part
      CreateTag("SrcPort", Tag("L4HeaderStart") + 0),
      Allocate(Tag("SrcPort"), 16),
      CreateTag("DstPort", Tag("L4HeaderStart") + 16),
      Allocate(Tag("DstPort"), 16),
      Assign(Tag("DstPort"), ConstantValue(80)),
      Assign(Tag("SrcPort"), SymbolicValue()),
      new NetFirewallRule(rule).symnetCode())
  } 
  
  def ex1(rule : FirewallRule) : Instruction = {
    genericExIp(rule, "192.168.0.1", "192.168.2.1")
  }
  
  def ex2(rule : FirewallRule) : Instruction = {
    genericExIp(rule, "1.1.1.1", "1.1.1.2")
  }
  
}