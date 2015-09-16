package org.change.v2.executor.clickabstractnetwork

import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.analysis.processingmodels._
import org.change.v2.executor.clickabstractnetwork.verificator.PathLocation

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class ClickExecutionAggregator(instructions: Map[LocationId, Instruction],
                               links: Map[LocationId, LocationId],
                               okStates: List[State],
                               failedStates: List[State],
                               stuckStates: List[State],
                               checkInstructions: Map[LocationId, Instruction] = Map.empty
                               )  extends ClickExecutionContext ( instructions, links, okStates, failedStates,
  stuckStates, checkInstructions)

object ClickExecutionAggregator {
  def freshAggregator( configs: Iterable[NetworkConfig],
                       interClickLinks: List[(PathLocation, PathLocation)]): ClickExecutionAggregator = {
    val ctxes = configs.map(c => c.id -> ClickExecutionContext(c))

    val configMap = configs.map(c => c.id -> c)

    ctxes.map(kctx => {
      val ctxId = kctx._1 + "-"
      val ctx = kctx._2
      ctxId -> new ClickExecutionContext(
        ctx.instructions.map({ki => (ctxId + ki._1) -> ki._2}),
        ctx.links.map({sd => (ctxId + sd._1) -> (ctxId + sd._2)}),
        Nil,
        Nil,
        Nil,
        ctx.checkInstructions.map({ki => (ctxId + ki._1) -> ki._2})
      )
    }).foldLeft(new ClickExecutionContext(
      Nil,
      interClickLinks.map()
    ))


  }
}
