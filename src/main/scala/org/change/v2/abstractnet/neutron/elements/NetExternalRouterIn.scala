package org.change.v2.abstractnet.neutron.elements

import java.io.PrintStream

import org.change.v2.abstractnet.neutron.NeutronWrapper
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions.Fail
import org.openstack4j.openstack.networking.domain.NeutronRouter

class NetExternalRouterIn(wrapper : NeutronWrapper, router : NeutronRouter, already : List[String] = Nil) 
    extends NetRouter(wrapper, router, already) {

    override def symnetCode() : Instruction = {
    if (router.getExternalGatewayInfo == null)
    {
      Fail("Cannot start off at an internal router")
    } else {
      super.symnetCode()
    }
  }
}



object NetExternalRouterIn {
  def apply(wrapper : NeutronWrapper, router : NeutronRouter) : Instruction = {
    apply(wrapper, router, Nil)
  }
  
  def apply(wrapper : NeutronWrapper, router : NeutronRouter, already : List[String]) : Instruction = {
    new NetExternalRouterIn(wrapper, router, already).symnetCode()
  }
  
  def main(argv : Array[String]) = {
   val wrapper = NeutronHelper.neutronWrapperFromFile("credentials2.txt")
   val r = wrapper.routerByName("toInside").asInstanceOf[NeutronRouter]
   val r2 = wrapper.routerByName("management").asInstanceOf[NeutronRouter]

   val pw = new PrintStream("file2.txt")
   
   val rin = NetExternalRouterIn(wrapper, r)
   val r2in  = NetExternalRouterIn(wrapper, r2)

   
   pw.println(rin)
   pw.println("===============")
   pw.println(r2in)
   
   pw.close()
  }
  
}