package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.expression.abst.{FloatingExpression, Expression}
import org.change.v2.analysis.processingmodels.{State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Rewrite(id: String, exp: FloatingExpression) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State): (List[State], List[State]) = {
    exp instantiate  s match {
      case Left(e) => optionToStatePair(s, "Error during 'rewrite'") {
        _.memory.Assign(id, e)
      }
      case Right(err) => Fail(err).apply(s)
    }

  }
}
