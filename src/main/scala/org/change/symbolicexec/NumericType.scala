package org.change.symbolicexec

trait NumericType extends Type {

  def name: String

  def min: Long = 0
  def max: Long = Long.MaxValue

}
