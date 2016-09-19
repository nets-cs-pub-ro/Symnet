package org.change.v2.abstractnet.neutron.elements

import scala.collection.JavaConversions._
import scala.collection.JavaConversions.asScalaSet

import org.change.v2.abstractnet.neutron.NeutronWrapper
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.instructions.If
import org.change.v2.analysis.processingmodels.instructions.NoOp
import org.change.v2.util.conversion.RepresentationConversion.ipToNumber
import org.openstack4j.model.network.HostRoute
import org.openstack4j.model.network.Subnet
import org.openstack4j.openstack.networking.domain.NeutronRouter
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.memory.Tag

class NetSubnetIn (wrapper : NeutronWrapper, subnet : Subnet) extends BaseNetElement(wrapper) {
  def symnetCode : Instruction = {
    val (start, end) = NetAddress(subnet.getCidr).addressRange
    If(Constrain(Tag("IPSrc"), :|:(:>:(ConstantValue(end)), :<:(ConstantValue(start)))),
        Fail("IPSrc not for this subnet " + subnet.getCidr),
        If(Constrain(Tag("IPDst"), :&:(:<=:(ConstantValue(end)), :>=:(ConstantValue(start)))),
            NoOp,
            parseLocalTable))
  }
  def parseLocalTable : Instruction = { 
   parseLocalTable(subnet.getHostRoutes.toList)
  }
  def parseLocalTable(routes : List[HostRoute]) : Instruction = routes match {
    case h::tail => {
      val (start, end) = NetAddress(h.getDestination).addressRange
      If (Constrain(Tag("IPDst"),:&:(:<=:(ConstantValue(end)), :>=:(ConstantValue(start)))),
          lookForNextElement(h.getNexthop),
          parseLocalTable(tail))
    }
    case Nil  => lookForGateway
  }
  
  def lookForNextElement(destination : String) : Instruction = {
    
    val ports = wrapper.ports.filter { x => x.getFixedIps.exists { y => y.getIpAddress == destination } }
    if (ports.length == 0)
      Fail("No port found with IP address "  + destination)
    else if (!ports.exists { x => x.getNetworkId == subnet.getNetworkId })
      Fail("Port found with IP address "  + destination + " found, but not attached to this network")
    else if (!ports.exists { x => x.getDeviceOwner == "network:router_interface" })
      Fail("Port found with IP address "  + destination + " found, attached to this network, but no router " )
    else {
      val filtered = ports.filter { x => x.getNetworkId == subnet.getNetworkId && x.getDeviceOwner == "network:router_interface" && x.getFixedIps.iterator().next().getIpAddress == destination }
      val port = filtered.get(0)
      val theRouter = wrapper.routerById(port.getDeviceId).asInstanceOf[NeutronRouter]
      NetRouter(wrapper, theRouter)
    }
  }
  
  def lookForGateway : Instruction = {
    if (subnet.getGateway != null && subnet.getGateway.length() != 0)
    {
      lookForNextElement(subnet.getGateway)
    }
    else
    {
      NoOp
    }
  } 
}

object NetSubnetIn {
  def apply(wrapper : NeutronWrapper, subnet : Subnet) : Instruction = {
    new NetSubnetIn(wrapper, subnet).symnetCode()
  }
  
  def main(argv : Array[String]) = {
      val nwrapper = NeutronHelper.neutronWrapperFromFile("credentials2.txt")
      nwrapper.getOs.networking().subnet().list.toList.foreach { x => System.out.println(NetSubnetIn(nwrapper, x)) }
      
  }
}