package org.change.symbolicexec.verifiablemodel

import org.change.symbolicexec.blocks.artificial.Spanner
import org.change.v2.abstractnet.click.ToDevice
import org.change.v2.abstractnet.generic.GenericElement

class Platform(val id: String) {

  val entry = new Spanner(id+"-entry")
  val exit = new Spanner(id+"-exit")
  exit.add

  lazy val spanners = Map( ((Platform.vmName, entry.id),platformRootNode), ((Platform.vmName, exit.id), platformLeafNode))

  val entryId = (Platform.vmName, entry.id)
  val exitId = (Platform.vmName, exit.id)

  val platformRootNode = new NetworkNode(entry.id, Platform.vmName, entry, "ISpanner")
  val platformLeafNode = new NetworkNode(exit.id, Platform.vmName, exit, "ESpanner")

  def insertFromRoot(vmId: String, elementId: String): Boolean = {
    val nextPort = entry.add
    platformRootNode.eLinks += ((nextPort, (0, (vmId, elementId))))
    true
  }

  def remove(vmId: String): Boolean =
    platformRootNode.eLinks.find(_._2._2._1 == vmId) match {
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
