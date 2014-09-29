package org.change.symbolicexec.networkgraph

import org.change.symbolicexec.blocks.ProcessingBlock
import scala.collection.mutable.{Map => MMap}

case class NetworkNode(val elementId: String,
                       val vmId: String,
                       var processingBlock: ProcessingBlock,
                       val eLinks: MMap[Int, (Int, (String, String))] = MMap()) {

}



