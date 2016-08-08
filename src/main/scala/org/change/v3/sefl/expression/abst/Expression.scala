package org.change.v3.sefl.expression.abst

import org.change.v2.analysis.memory.State

import scala.util.Random



/**
 * An expression without dangling references, only concrete values.
 * @param id Every expression has an id for an easy equality check.
 */
abstract class Expression(val id: Long = Expression.randomId)

object Expression {
  lazy val randomizer = new Random()
  def randomId: Long = randomizer.nextLong()
}