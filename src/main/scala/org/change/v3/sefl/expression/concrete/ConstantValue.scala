package org.change.v3.sefl.expression.concrete

import org.change.v3.sefl.expression.abst.{Expression, FloatingExpression}
import org.change.v2.analysis.memory.State

/**
 * Created by radu on 3/24/15.
 */
case class ConstantValue(value: Long) extends Expression with FloatingExpression {

  /**
   * A floating expression may include unbounded references (e.g. symbol ids)
   *
   * Given a context (the state) it can produce an evaluable expression.
   * @param s
   * @return
   */
  override def instantiate(s: State): Either[Expression, String] = Left(this)

  override def toString = s"Const($value)]"
}
