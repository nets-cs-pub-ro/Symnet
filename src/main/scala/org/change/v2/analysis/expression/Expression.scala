package org.change.v2.analysis.expression

import scala.util.Random

/**
 * Created by radu on 3/24/15.
 */
abstract class Expression(val id: Long)

object Expression {
  lazy val randomizer = new Random()

  def randomId: Long = randomizer.nextLong()
}