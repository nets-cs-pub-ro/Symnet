package org.change.v2.runners.experiments

import java.io.{File, FileOutputStream, PrintStream}

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

    val successful = genTest._1
    val  failed = genTest._2

    val outputOk = new PrintStream(new FileOutputStream(new File("file_subnet_" + name + "_run_ok.json")))

    outputOk.println(
      successful.map(_.jsonString).mkString("[", ",\n","]")
    )

    outputOk.close()

    val outputFail = new PrintStream(new FileOutputStream(new File("file_subnet_" + name + "_run_fail.json")))

    outputFail.println(
      failed.map(_.jsonString).mkString("[", ",\n", "]")
    )

    outputFail.close()

    println(s"Done: ${successful.length} ok, ${failed.length} failed")
  }
  
  def runFromExternal(wrapper : NeutronWrapper, name : String) {
    val r = wrapper.routerByName(name).asInstanceOf[NeutronRouter]
    val pw = new PrintStream("file_router_" + name + "_instr.txt")
    val instrs = NetExternalRouterIn(wrapper, r)
    pw.println(instrs + "\n")
    pw.println("===============\n\n")
    pw.close()
    
    val genTest = genericTest(instrs)(State.clean, true)
    val successful = genTest._1
    val  failed = genTest._2

    val outputOk = new PrintStream(new FileOutputStream(new File("file_subnet_" + name + "_run_ok.json")))

    outputOk.println(
      successful.map(_.jsonString).mkString("[", ",\n","]")
    )

    outputOk.close()

    val outputFail = new PrintStream(new FileOutputStream(new File("file_subnet_" + name + "_run_fail.json")))

    outputFail.println(
      failed.map(_.jsonString).mkString("[", ",\n", "]")
    )

    outputFail.close()

    println(s"Done: ${successful.length} ok, ${failed.length} failed")
  }
}