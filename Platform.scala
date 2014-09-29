package org.change.symbolicexec.networkgraph

import org.change.symbolicexec.blocks.ProcessingBlock
import org.change.symbolicexec.blocks.artificial.Spanner
import parser.generic.GenericElement
import parser.specific.ToDevice

class Platform(id: String) {

  val entry = new Spanner(id+"-entry")
  val exit = new Spanner(id+"-exit")
  lazy val spanners = Map( ((Platform.vmName, entry.id),entry), ((Platform.vmName, exit.id), exit))

  val platformRootNode = new NetworkNode(entry.id, Platform.vmName)
  val platformLeafNode = new NetworkNode(exit.id, Platform.vmName)

  def insert(vm: NetworkNode, elements: Map[String, GenericElement]): Boolean = {
    vm.findFirstThat(n => elements(n.elementId) match {
      case _:ToDevice => true
      case _ => false
    }) match {
      case Some(vmExit) => {
        vmExit.addExplicitLink(platformLeafNode, 0, 0)
        val nextPort = entry.add
        platformRootNode.addExplicitLink(vm, nextPort, 0)
        true
      }
      case None => false
    }
  }

  def remove(vmId: String): Boolean =
    platformRootNode.eLinks.find(_._2._1.vmId == vmId) match {
      case Some((port, _)) => {
        entry.remove(port)
        platformRootNode.eLinks -= (port)
        true
      }
      case None => false
    }

}

object Platform {
  val vmName = "isp"
}
