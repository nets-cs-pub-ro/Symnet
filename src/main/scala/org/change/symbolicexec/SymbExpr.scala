package org.change.symbolicexec

import org.change.symbolicexec.types.NumericType

/**
 * A value that can be attributed to a memory symbol.
 * @param constraints A set of expressions that define its possible set of values.
 * @param valueType The type of the value, determining its initial range.
 * @param evalCache Cache for storing intermediate results after evaluating the
 *                  list of constraints.
 */
case class SymbExpr(constraints: List[Constraint],
                    valueType: NumericType = NumericType(),
//This is set whenever the constraint list is evaluated
                 var evalCache: Option[List[Interval]] = None) {

//  If no cache to start with, compute it, eagerly so far
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
   * If not, an execution path containing such a value is impossible to reach.
   */
  def valid: Boolean = ! eval.isEmpty

  /**
   * Returns a new value obtained from further constraining of this value.
   * @param c The new additional constraint.
   * @return A new value.
   */
  def constrain(c: Constraint): SymbExpr =
    SymbExpr(c :: constraints, valueType, Some(applyConstraint(eval, c, valueType)))

  /**
   * Enforce a list of constraints against the current value.
   * @param cs The constraints.
   * @return A new value.
   */
  def constrain(cs: List[Constraint]): SymbExpr =
    SymbExpr(cs ++ constraints, valueType, Some(cs.foldLeft(eval)((cache, c) => applyConstraint(cache, c, valueType))))

}

object SymbExpr {
  /**
   * Easily build a new value with an initial constraint attached.
   * @param c
   * @param valueType
   * @return
   */
  def fromConstraint(c: Constraint, valueType: NumericType = NumericType()): SymbExpr = SymbExpr(List(c), valueType)
  def fromConstraints(cs: List[Constraint], valueType: NumericType = NumericType()): SymbExpr = SymbExpr(cs, valueType)
  def unconstrained(valueType: NumericType = NumericType()): SymbExpr = SymbExpr(Nil, valueType)
}
