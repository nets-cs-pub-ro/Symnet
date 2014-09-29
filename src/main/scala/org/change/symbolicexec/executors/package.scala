package org.change.symbolicexec

import org.change.symbolicexec.networkgraph.NetworkNode
import parser.generic.NetworkConfig

package object executors {

  type ExecutableModel = scala.collection.mutable.Map[(String, String), NetworkNode]

  def elementsToExecutableModel(parsedModel: NetworkConfig, vmId: String): ExecutableModel = {
    val blocks = parsedModel.elements.map( e => ((vmId, e._1), new NetworkNode(vmId, e._1, e._2.toProcessingBlock)))
    for {
      path <- parsedModel.paths
      link <- path.sliding(2)
      source = link.head
      destination = link.last
    } {
      if (blocks.contains((vmId, source._1)) && blocks.contains((vmId, destination._1)))
        blocks((vmId, source._1)).eLinks += ((source._3, (destination._2, (vmId, destination._1))))
    }

    collection.mutable.Map(blocks.toSeq: _*)
  }


}
