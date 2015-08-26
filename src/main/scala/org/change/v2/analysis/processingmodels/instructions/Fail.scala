package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.processingmodels.{State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Fail(errMsg: String = "Fail op executed")  extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) =
    stateToError(if (v) s.addInstructionToHistory(this) else s, errMsg)
}
