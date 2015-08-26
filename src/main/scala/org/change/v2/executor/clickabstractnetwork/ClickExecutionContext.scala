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

  override def execute(verbose: Boolean = false): ClickExecutionContext = {
    val (ok, fail, stuck) = (for {
      sPrime <- okStates
      s = if (links contains sPrime.location)
          sPrime.forwardTo(links(sPrime.location))
        else
          sPrime
      stateLocation = s.location
    } yield {
        if (instructions contains stateLocation) {
          val r = instructions(stateLocation)(s, verbose)
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

  private def verboselyStringifyStates(ss: List[State]): String = ss.zipWithIndex.map( si =>
    "State #" + si._2 + "\n\n" + si._1.instructionHistory.reverse.mkString("\n") + "\n\n" + si._1.toString)
    .mkString("\n")

  def stringifyStates(includeOk: Boolean = true, includeStuck: Boolean = true, includeFailed: Boolean= true) = {
    (if (includeOk)
      s"Ok states (${okStates.length}):\n" + verboselyStringifyStates(okStates)
    else
      "") +
    (if (includeStuck)
      s"Stuck states (${stuckStates.length}):\n" + verboselyStringifyStates(stuckStates)
    else
      "") +
    (if (includeFailed)
      s"Failed states (${stuckStates.length}): \n" + verboselyStringifyStates(failedStates)
    else
      "")
  }
}
