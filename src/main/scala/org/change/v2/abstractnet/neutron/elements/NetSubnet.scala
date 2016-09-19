package org.change.v2.abstractnet.neutron.elements

import scala.collection.JavaConversions.asScalaSet
import scala.collection.JavaConversions._

import org.change.v2.abstractnet.neutron.NeutronWrapper
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.memory.Tag
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions.{ :==: => :==: }
import org.change.v2.analysis.processingmodels.instructions.Constrain
import org.change.v2.analysis.processingmodels.instructions.Fail
import org.change.v2.analysis.processingmodels.instructions.If
import org.change.v2.analysis.processingmodels.instructions.NoOp
import org.change.v2.util.conversion.RepresentationConversion.ipToNumber
import org.openstack4j.model.network.IP
import org.openstack4j.model.network.Port
import org.openstack4j.model.network.Subnet

class NetSubnet(wrapper : NeutronWrapper, subnet : Subnet) extends BaseNetElement(wrapper) {
  def symnetCode : Instruction = {
    val netPorts = this.portsByCidr(subnet.getCidr).filter { x => x.getNetworkId == subnet.getNetworkId }
    symnetCode(netPorts)
  }
  
  private def symnetCode(list : List[IP], tail2 : List[Port]) : Instruction = list match {
    case h :: tail => {
          If (Constrain(Tag("IPDst"), :==:(ConstantValue(ipToNumber(h.getIpAddress)))),
              NoOp,
              symnetCode(tail, tail2))
    }
    case Nil => symnetCode(tail2)
  }
  
  private def symnetCode(list : List[Port]) : Instruction = list match {
    case h :: tail => symnetCode(h.getFixedIps.toList, tail)
    case Nil => Fail("No such address")
  }
  
}

object NetSubnet {
  def apply(wrapper : NeutronWrapper, subnet : Subnet) : Instruction = {
    new NetSubnet(wrapper, subnet).symnetCode()
  }
  
  def main(argv : Array[String]) = {
      val nwrapper = NeutronHelper.neutronWrapperFromFile()
      nwrapper.getOs.networking().subnet().list.toList.foreach { x => System.out.println(NetSubnet(nwrapper, x)) }
      
  }
}