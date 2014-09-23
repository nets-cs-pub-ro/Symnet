package org.change.symbolicexec

import parser.generic.NetworkConfig

package object executors {

  def elementsToProcessingBlocks(parsedModel: NetworkConfig) = parsedModel.elements.map( e => (e._1, e._2.toProcessingBlock))

}
