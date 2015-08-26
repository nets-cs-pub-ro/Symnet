package org.change.v2.analysis.expression.abst


import org.change.v2.analysis.processingmodels.{State, Instruction}
import org.change.v2.analysis.z3.Z3Able

import scala.util.Random

/**
 * Created by radu on 3/24/15.
 *
 * A floating expression is a formula with references not yet bound to values.
 *
 * These bindings are made according to the context of a state.
 */
trait FloatingExpression {
  /**
   * A floating expression may include unbounded references (e.g. symbol ids)
   *
   * Given a context (the state) it can produce an evaluable expression.
   * @param s
   * @return
   */
  def instantiate(s: State): Either[Expression, String]
}

/**
 * An expression without dangling references.
 * @param id Every expression has an id for an easy equality check.
 */
abstract class Expression(val id: Long = Expression.randomId) extends Z3Able

object Expression {
  lazy val randomizer = new Random()
  def randomId: Long = randomizer.nextLong()
}