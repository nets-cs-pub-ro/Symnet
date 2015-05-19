package org.change.v2.analysis.processingmodels

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class InstructionBlock(instructions: List[Instruction]) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State): (List[State], List[State]) =
    instructions.foldLeft((List(s), Nil: List[State])) { (acc, i) => {
        val (valid: List[State], failed: List[State]) = acc
        val (nextValid, nextFailed) = valid.map(i(_)).unzip
        val allValid = nextValid.foldLeft(Nil: List[State])(_ ++ _)
        val allFailed = nextFailed.foldLeft(failed: List[State])(_ ++ _)
        (allValid, allFailed)
      }
    }
}
