package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.expression.abst.{FloatingExpression, Expression}
import org.change.v2.analysis.processingmodels.{State, Instruction}
import org.change.v2.analysis.types.{LongType, NumericType}
import org.change.v2.analysis.memory.{TagExp, Intable}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class AssignNamedSymbol(id: String, exp: FloatingExpression, t: NumericType = LongType) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) = {
    exp instantiate  s match {
      case Left(e) => optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Error during assignment of $id") (s => {
        s.memory.Assign(id, e, t)
      })
      case Right(err) => Fail(err).apply(s, v)
    }
  }
}

case class AssignRaw(a: Intable, exp: FloatingExpression, t: NumericType = LongType) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) = a(s) match {
    case Some(int) => { exp instantiate  s match {
      case Left(e) => optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Error during assignment at $a") (s => {
        s.memory.Assign(int, e)
      })
      case Right(err) => Fail(err).apply(s, v)
    }}
    case None => Fail(TagExp.brokenTagExpErrorMessage)(s,v)
  }
}

object Assign {
  def apply(a: Intable, exp: FloatingExpression): Instruction =
    AssignRaw(a, exp, LongType)
  def apply(id: String, exp: FloatingExpression): Instruction =
    AssignNamedSymbol(id, exp, LongType)
}