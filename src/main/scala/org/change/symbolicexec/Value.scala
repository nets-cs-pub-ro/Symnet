package org.change.symbolicexec

case class Value(valueType: NumericType, constraints: List[Constraint], evalCache: List[Interval] = Nil) {

  /**
   * Returns the set of possible concrete values given the constraints, from cache.
   * These may not be valid anymore.
   */
  def eval: List[Interval] = evalCache

  /**
   * Determines the set of possible concrete values given the constraints.
   * The results are computed then cached.
   */
  def forceEval: List[Interval] = evalCache

  /**
   * Are there any possible values ?
   */
  def valid: Boolean = ! eval.isEmpty

}
