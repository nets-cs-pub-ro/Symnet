package org.change.v2.executor.clickabstractnetwork

import org.change.v2.analysis.processingmodels.{LocationId, Instruction, State}
import org.change.v2.executor.ExecutionContext

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 *
 * An execution context is determined by the instructions it can execute and
 * a set of states that were explored.
 *
 * A port is an Int, that maps to an instruction.
 *
 */
class ClickExecutionContext(
                           val instructions: Map[LocationId, Instruction],
                           val links: Map[LocationId, LocationId],
                           val okStates: List[State],
                           val failedStates: List[State],
                           val stuckStates: List[State]
                             ) extends ExecutionContext {

  override def execute: ClickExecutionContext = {
    val (ok, fail, stuck) = (for {
      sPrime <- okStates
      s = if (links contains sPrime.location)
          sPrime.forwardTo(links(sPrime.location))
        else
          sPrime
      stateLocation = s.location
    } yield {
        if (instructions contains stateLocation) {
          val r = instructions(stateLocation)(s)
          (r._1, r._2, Nil)
        } else
          (Nil, Nil, List(s))
      }).unzip3

      new ClickExecutionContext(instructions,
        links,
        ok.flatten,
        failedStates ++ fail.flatten,
        stuckStates ++ stuck.flatten)
  }
}
