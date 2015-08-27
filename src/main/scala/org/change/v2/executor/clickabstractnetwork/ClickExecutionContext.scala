package org.change.v2.executor.clickabstractnetwork

import org.change.symbolicexec.verification.Rule
import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.analysis.processingmodels.instructions.InstructionBlock
import org.change.v2.analysis.processingmodels.{LocationId, Instruction, State}

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
                           val stuckStates: List[State],
                           val checkInstructions: Map[LocationId, Instruction] = Map.empty
) {

  def isDone: Boolean = okStates.isEmpty

  def execute(verbose: Boolean = false): ClickExecutionContext = {
    val (ok, fail, stuck) = (for {
      sPrime <- okStates
      s = if (links contains sPrime.location)
          sPrime.forwardTo(links(sPrime.location))
        else
          sPrime
      stateLocation = s.location
    } yield {
        if (instructions contains stateLocation) {
//          Apply instructions
          val r1 = instructions(stateLocation)(s, verbose)
//          Apply check instructions on output ports
          val (toCheck, r2) = r1._1.partition(s => checkInstructions.contains(s.location))
          val r3 = toCheck.map(s => checkInstructions(s.location)(s,verbose)).unzip
          (r2 ++ r3._1.flatten, r1._2 ++ r3._2.flatten, Nil)
        } else
          (Nil, Nil, List(s))
      }).unzip3

      new ClickExecutionContext(instructions,
        links,
        ok.flatten,
        failedStates ++ fail.flatten,
        stuckStates ++ stuck.flatten,
        checkInstructions
      )
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

object ClickExecutionContext {
  def apply(networkModel: NetworkConfig, verificationConditions: List[Rule] = Nil): ClickExecutionContext = {
    val instructions = networkModel.elements.values.foldLeft(Map[LocationId, Instruction]())(_ ++ _.instructions)
    val checkInstructions = verificationConditions.map( r => {
        (networkModel.elements(r.where.element).outputPortName(r.where.port)) -> InstructionBlock(r.whatTraffic)
      }).toMap

    val links = networkModel.paths.flatMap( _.sliding(2).map(pcp => {
      val src = pcp.head
      val dst = pcp.last
      (networkModel.elements(src._1).outputPortName(src._3) -> networkModel.elements(dst._1).inputPortName(dst._2))
    })).toMap

    val initialState = State.bigBang.forwardTo(networkModel.entryLocationId)

    new ClickExecutionContext(instructions, links, List(initialState), Nil, Nil, checkInstructions)
  }
}
