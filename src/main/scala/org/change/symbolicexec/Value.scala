package org.change.symbolicexec

case class Value(valueType: NumericType, constraints: List[Constraint], evalCache: List[Interval] = Nil) {

  def eval: List[Interval] = evalCache
  def forceEval: List[Interval] = evalCache
  def valid: Boolean = ! eval.isEmpty

}
