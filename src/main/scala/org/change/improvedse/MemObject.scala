package org.change.improvedse

import org.change.symbolicexec._
import org.change.symbolicexec.types.NumericType

/**
 * Created by radu on 22.01.2015.
 */
abstract class MemObject(val constraints: List[Constraint] = Nil,
                         val valueType: NumericType = NumericType(),
                         var evalCache: Option[List[Interval]] = None) {

  /**
   * Returns the possible domain of a value from the cache (if present), or
   * it freshly computes it and updates the cache otherwise.
   *
   * For symbolic expressions the result is None.
   */
  def eval: Option[ValueSet] = Some(evalCache.getOrElse(forceEval))

  /**
   * Recomputes the possible domain of a symbol and updates the cache.
   */
  def forceEval: List[(Long, Long)] = {
    val vs = constraints.foldLeft(valueType.admissibleSet)((s, c) => applyConstraint(s,c,valueType))
    evalCache = Some(vs)
    vs
  }

  /**
   * For symbolic expressions no admissible set is computed, they are considered valid, by default.
   * @return
   */
  def valid: Boolean = eval match {
    case Some(set) => ! set.isEmpty
    case None => true
  }

  def constrain(c: Constraint): MemObject

  def constrain(cs: List[Constraint]): MemObject

  def isSymbolicExpression: Boolean = false
}
