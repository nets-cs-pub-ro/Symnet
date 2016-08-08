package org.change.v3.sefl.expression.concrete

import org.change.v3.sefl.expression.abst.{Expression, FloatingExpression}
import org.change.v2.analysis.memory.{State, Value}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Plus(a: Value, b: Value) extends Expression {
    override def toString = s"(${a.e} + ${b.e})"
}

case class :+:(left: FloatingExpression, right: FloatingExpression) extends FloatingExpression {
  /**
   * A floating expression may include unbounded references (e.g. symbol ids)
   *
   * Given a context (the state) it can produce an evaluable expression.
   * @param s
   * @return
   */
  override def instantiate(s: State): Either[Expression, String] = {
    (left instantiate s) match {
      case Left(e1) => (right instantiate s) match {
        case Left(e2) => Left(Plus(Value(e1), Value(e2)))
        case cause2@Right(_) => cause2
      }
      case cause1@Right(_) => cause1
    }
  }
}
