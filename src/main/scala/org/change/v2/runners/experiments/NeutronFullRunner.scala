package org.change.v2.runners.experiments

import java.io.PrintStream

import org.change.v2.abstractnet.neutron.NeutronWrapper
import org.change.v2.abstractnet.neutron.elements.{NetExternalRouterIn, NetSubnetIn}
import org.change.v2.abstractnet.neutron.elements.NeutronHelper._
import org.change.v2.analysis.memory.State
import org.change.v2.runners.experiments.NeutronRunner._
import org.openstack4j.openstack.networking.domain.NeutronRouter

import scala.collection.JavaConversions._


object NeutronFullRunner {

   def main(argv : Array[String]) {    
    val (apiAddr, userName, password, project) = readCredentials("credentials2.txt")
    
    val wrapper = new NeutronWrapper(apiAddr,
          userName,
          password,
          project)

//    runFromSubnet(wrapper, "private")
//    runFromSubnet(wrapper, "private")
    runFromExternal(wrapper, "toInside")
//    runFromExternal(wrapper, "vlan6")
  }
   
  def runFromSubnet(wrapper : NeutronWrapper, name : String) {
    val subsInside = wrapper.subnet(name)
    val subInside = subsInside.get(0)
    val instrs = NetSubnetIn(wrapper, subInside)
    val ps = new PrintStream("file_subnet_" + name + "_instr.txt")
    ps.println(instrs + "\n")
    ps.println("===============\n\n")
    ps.close()
    
    val genTest = genericTest(instrs)(State.clean, true)
    val ps2 = new PrintStream("file_subnet_" + name + "_run.txt")
    ps2.println(genTest + "\n")
    ps2.println("===============\n\n")
    ps2.close()
  }
  
  def runFromExternal(wrapper : NeutronWrapper, name : String) {
    val r = wrapper.routerByName(name).asInstanceOf[NeutronRouter]
    val pw = new PrintStream("file_router_" + name + "_instr.txt")
    val instrs = NetExternalRouterIn(wrapper, r)
    pw.println(instrs + "\n")
    pw.println("===============\n\n")
    pw.close()
    
    val genTest = genericTest(instrs)(State.clean, true)
    val ps2 = new PrintStream("file_router_" + name + "_run.txt")
    ps2.println(genTest + "\n")
    ps2.println("===============\n\n")
    ps2.close()
  }
}