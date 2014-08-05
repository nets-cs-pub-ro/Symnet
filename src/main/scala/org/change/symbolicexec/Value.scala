package org.change.symbolicexec

/**
 * Warning, eval Cache should be made Option since Nil is a valid value.
 * @param constraints
 * @param valueType
 * @param evalCache
 */
case class Value(constraints: List[Constraint], valueType: NumericType = NumericType(), var evalCache: List[Interval] = Nil) {

  /**
   * Returns the set of possible concrete values given the constraints, from cache.
   * These may not be valid anymore.
   */
  def eval: List[Interval] = evalCache

  /**
   * Determines the set of possible concrete values given the constraints.
   * The results are computed then cached.
   */
  def forceEval: List[Interval] = {
    evalCache = constraints.foldLeft(valueType.admissibleSet)( (s, c) => applyConstraint(s,c,valueType))
    evalCache
  }

  /**
   * Are there any possible values ?
   */
  def valid: Boolean = ! eval.isEmpty

}
