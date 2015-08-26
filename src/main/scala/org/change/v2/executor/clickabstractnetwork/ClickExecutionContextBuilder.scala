package org.change.v2.executor.clickabstractnetwork

import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.analysis.processingmodels.{State, LocationId, Instruction}
import org.change.v2.executor.{ExecutionContext, ExecutionContextBuilder}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object ClickExecutionContextBuilder extends ExecutionContextBuilder[NetworkConfig] {
  override def buildExecutionContext(networkModel: NetworkConfig): ClickExecutionContext = {
    val instructions = networkModel.elements.values.foldLeft(Map[LocationId, Instruction]())(_ ++ _.instructions)
    val links = networkModel.paths.flatMap( _.sliding(2).map(pcp => {
      val src = pcp.head
      val dst = pcp.last
      (networkModel.elements(src._1).outputPortName(src._3) -> networkModel.elements(dst._1).inputPortName(dst._2))
    })).toMap

    val initialState = State.bigBang.forwardTo(networkModel.entryLocationId)

    new ClickExecutionContext(instructions, links, List(initialState), Nil, Nil)
  }
}
