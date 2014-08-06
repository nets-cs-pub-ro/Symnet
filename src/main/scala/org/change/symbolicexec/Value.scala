package org.change.symbolicexec

import org.change.symbolicexec.types.NumericType

case class Value(constraints: List[Constraint], valueType: NumericType = NumericType(), var evalCache: Option[List[Interval]] = None) {

//  If no cache to start with, compute it
  evalCache match {
    case None => evalCache = Some(constraints.foldLeft(valueType.admissibleSet)((s, c) => applyConstraint(s,c,valueType)))
    case Some(_) =>
  }

  /**
   * Returns the set of possible concrete values given the constraints, from cache.
   * These may not be valid anymore.
   */
  def eval: List[Interval] = evalCache match {
    case None => forceEval
    case Some(vals) => vals
  }

  /**
   * Determines the set of possible concrete values given the constraints.
   * The results are computed then cached.
   */
  def forceEval: List[Interval] = {
    /**
     * Compute and store
     */
    val vs = constraints.foldLeft(valueType.admissibleSet)((s, c) => applyConstraint(s,c,valueType))
    evalCache = Some(vs)
    vs
  }

  /**
   * Are there any possible values ?
   */
  def valid: Boolean = ! eval.isEmpty

  def constrain(c: Constraint): Value =
    Value(c :: constraints, valueType, Some(applyConstraint(eval, c, valueType)))

  def constrain(cs: List[Constraint]): Value =
    Value(cs ++ constraints, valueType, Some(cs.foldLeft(eval)((cache, c) => applyConstraint(cache, c, valueType))))

}

object Value {
  /**
   * Easily build a new value with an initial constraint attached.
   * @param c
   * @param valueType
   * @return
   */
  def fromConstraint(c: Constraint, valueType: NumericType = NumericType()): Value = Value(List(c), valueType)

  def unconstrained(valueType: NumericType = NumericType()): Value = Value(Nil, valueType)
}
