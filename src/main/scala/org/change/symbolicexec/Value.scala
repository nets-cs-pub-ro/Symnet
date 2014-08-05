package org.change.symbolicexec

case class Value(valueType: NumericType, constraints: List[Constraint]) {

  def admissibleValues: List[Interval] = Nil
  def valid: Boolean = ! admissibleValues.isEmpty

}
