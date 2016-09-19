package org.change.v2.abstractnet.neutron.elements

import scala.collection.JavaConversions._
import scala.collection.mutable.Buffer

import org.change.v2.abstractnet.neutron.NeutronWrapper
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.memory.Tag
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.instructions.Constrain
import org.change.v2.analysis.processingmodels.instructions.Fail
import org.change.v2.analysis.processingmodels.instructions.If
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.conversion.RepresentationConversion._
import org.openstack4j.api.OSClient
import org.openstack4j.model.network.Router
import org.openstack4j.model.network.Subnet
import org.openstack4j.openstack.networking.domain.NeutronRouter

class NetRouter(wrapper : NeutronWrapper, router : NeutronRouter, already : List[String] = Nil) 
    extends BaseNetElement(wrapper) {
  
  
  
  def checkDestNearby() : Instruction = {
    val thList = wrapper.getOs().networking().port().list
    val filtered = thList.filter { x => 
      x.getDeviceId == router.getId 
    }
    null
  }
  
  def routerNets() = {
    ports.filter { z => z.getDeviceId == router.getId }.map { x => 
            val ips = x.getFixedIps.iterator()
            if (ips.hasNext()) {
              val ip = ips.next
              val theSubnet = subnet.get(ip.getSubnetId)
              (ip.getIpAddress, new NetAddress(theSubnet.getCidr), theSubnet);
            } else {
              null
            }
         }.sortBy { u => - u._2.mask }
  }
  
  
  private def checkMyDestination(list : List[(String, NetAddress, Subnet)]) : Instruction = 
  list match {
    case h :: tail => If (Constrain(Tag("IPDst"),  :==:(ConstantValue(ipToNumber(h._1)))),
        NoOp,
        checkMyDestination(tail))
    case Nil => checkConnectedSubnet
  }
  
  private def checkMyDestination() : Instruction = {
    checkMyDestination(this.routerNets.toList)
  }
  
  private def checkConnectedSubnet(list :  List[(String, NetAddress, Subnet)]) : Instruction = 
  list match {
    case h :: tail => If (Constrain(Tag("IPDst"), :&:(:>:(ConstantValue(h._2.addressRange._1)),
        :<:(ConstantValue(h._2.addressRange._2)))),
        NetSubnet(wrapper, h._3),
        checkConnectedSubnet(tail))
    case Nil => checkRoutingTable
  }
  
  private def checkConnectedSubnet() : Instruction = {
    checkConnectedSubnet(this.routerNets.toList)
  }
  
  private def checkRoutingTable(list : List[(NetAddress, String)]) : Instruction = 
  list match {
    case h :: tail => If (Constrain(Tag("IPDst"), :&:(:>:(ConstantValue(h._1.addressRange._1)),
        :<:(ConstantValue(h._1.addressRange._2)))),
        {
          val rip = routerByIp(h._2)
          if (rip != null)
          {
            NetRouter(wrapper, rip.asInstanceOf[NeutronRouter], (already.::(router.getId)))
          }
          else
          {
            Fail("No router at address " + h._2)
          }
        },
        checkRoutingTable(tail))
    case Nil => checkExternalGateway
  }
  
  private def checkExternalGateway() : Instruction = {
//    Fail("No route to host")

    if (this.router.getExternalGatewayInfo != null)
    {
      // TBD : what to do when external router is there
      NoOp
    }
    else
    {
      Fail("No route to host")
    }
  }
  
  private def checkRoutingTable() : Instruction = {
    checkRoutingTable(this.router.getRoutes.map { x => 
      (new NetAddress(x.getDestination), x.getNexthop) 
    }.toList)
    
  }
  

  
  def symnetCode() : Instruction = {
    if (already.contains(router.getId))
    {
      Fail("Already been there")
    }
    else
    {
      checkMyDestination
    }
  }
}



object NetRouter {
  def apply(wrapper : NeutronWrapper, router : NeutronRouter) : Instruction = {
    apply(wrapper, router, Nil)
  }
  
  def apply(wrapper : NeutronWrapper, router : NeutronRouter, already : List[String]) : Instruction = {
    new NetRouter(wrapper, router, already).symnetCode()
  }
  
  def main(argv : Array[String]) = {
   val wrapper = new NeutronWrapper("https://cloud-controller.grid.pub.ro:5000/v2.0",
        "dragos.dumitrescu92",
        "Oximoron_16092", "dragos.dumitrescu92_prj");
   val r = wrapper.getOs.networking.router.list.get(0).asInstanceOf[NeutronRouter]
   System.out.println(NetRouter(wrapper, r))    
  }
  
}