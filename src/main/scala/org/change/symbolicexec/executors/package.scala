package org.change.symbolicexec

import parser.generic.NetworkConfig

package object executors {

  def elementsToProcessingBlocks(parsedModel: NetworkConfig, vmId: String = "vm") =
    parsedModel.elements.map( e => ((vmId, e._1), e._2.toProcessingBlock))

}
