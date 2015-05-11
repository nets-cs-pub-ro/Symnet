package org.change.v2.analysis.expression.abst

import org.change.v2.analysis.z3.Z3Able

import scala.util.Random

/**
 * Created by radu on 3/24/15.
 */
abstract class Expression(val id: Long = Expression.randomId) extends Z3Able

object Expression {
  lazy val randomizer = new Random()

  def randomId: Long = randomizer.nextLong()
}