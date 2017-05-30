package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.memory.State
import org.change.v2.analysis.processingmodels.Instruction

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class InstructionBlock(instructions: Iterable[Instruction]) extends Instruction {
  /**
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) = {
    val initialHistory = s.history

    instructions.foldLeft((List(s), Nil: List[State])) { (acc, i) => {
        val (valid, failed) = acc
        val (forwarded, notForwarded) = valid.partition(_.history != s.history)
        val (nextValid, nextFailed) = notForwarded.map(i(_, v)).unzip
        val allValid = nextValid.foldLeft(forwarded)(_ ++ _)
        val allFailed = nextFailed.foldLeft(failed)(_ ++ _)
        (allValid, allFailed)
      }
    }
  }
}

object InstructionBlock {
  def apply(instrs: Instruction*): InstructionBlock = InstructionBlock(instrs.toList)
}
