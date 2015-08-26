package org.change.symbolicexec.verifiablemodel

import org.change.symbolicexec.blocks.ProcessingBlock
import scala.collection.mutable.{Map => MMap}

case class NetworkNode(val elementId: String,
                       val vmId: String,
                       var processingBlock: ProcessingBlock,
                       val elementType: String = "unknown",
                       val eLinks: MMap[Int, (Int, (String, String))] = MMap()) {

}



