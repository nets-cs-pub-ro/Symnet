package org.change.symbolicexec.types

import org.change.symbolicexec._

/**
 * Warning: This code looks quite silly, but ok for the moment.
 */
class NumericType extends Type {
  override def name = "GenericNumeric"
  def min: Long = 0
  def max: Long = Long.MaxValue
  def admissibleRange: Interval = (min, max)
  def admissibleSet: ValueSet = List(admissibleRange)
}

object NumericType {
  private lazy val instance = new NumericType

  def apply() = instance
}