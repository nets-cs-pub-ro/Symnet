package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.processingmodels.{State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Rewrite(id: String, exp: Expression) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State): (List[State], List[State]) = {
    optionToStatePair(s, "Could not perform rewrite") {
      _.memory.REWRITE(id, exp)
    }
  }
}
