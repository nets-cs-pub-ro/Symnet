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
    /**
     * Compute and store
     */
    evalCache = constraints.foldLeft(valueType.admissibleSet)( (s, c) => applyConstraint(s,c,valueType))
    evalCache
  }

  /**
   * Are there any possible values ?
   */
  def valid: Boolean = ! eval.isEmpty

}

object Value {
  /**
   * Easily build a new value with an initial constraint attached.
   * @param c
   * @param valueType
   * @return
   */
  def apply(c: Constraint, valueType: NumericType = NumericType()): Value = Value(List(c), valueType)
}
