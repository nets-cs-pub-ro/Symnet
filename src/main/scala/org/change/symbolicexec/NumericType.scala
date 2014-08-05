package org.change.symbolicexec

trait NumericType extends Type {

  def min: Long = 0
  def max: Long = Long.MaxValue
  def admissibleRange: Interval = (min, max)
}
