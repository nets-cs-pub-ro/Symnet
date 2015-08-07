package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.expression.abst.{FloatingExpression, Expression}
import org.change.v2.analysis.processingmodels.{State, Instruction}
import org.change.v2.analysis.types.{LongType, NumericType}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Assign(id: String, exp: FloatingExpression, t: NumericType = LongType) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State): (List[State], List[State]) = {
    exp instantiate  s match {
      case Left(e) => optionToStatePair(s, s"Error during assignment of $id") {
        _.memory.Assign(id, e, t)
      }
      case Right(err) => Fail(err).apply(s)
    }

  }
}
