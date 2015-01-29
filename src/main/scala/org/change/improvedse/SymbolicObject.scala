package org.change.improvedse

import org.change.symbolicexec.types.NumericType
import org.change.symbolicexec._

/**
 * A value that can be attributed to a memory symbol.
 * @param constraints A set of expressions that define its possible set of values.
 * @param valueType The type of the value, determining its initial range.
 * @param cache Cache for storing intermediate results after evaluating the
 *                  list of constraints.
 */
class SymbolicObject(constraints: List[Constraint] = Nil,
                    valueType: NumericType = NumericType(),
                    //This is set whenever the constraint list is evaluated
                    cache: Option[List[Interval]] = None) extends MemObject(constraints, valueType, cache) {

  //  If no cache to start with, compute it, eagerly so far
  evalCache match {
    case None => evalCache = Some(constraints.foldLeft(valueType.admissibleSet)((s, c) => applyConstraint(s,c,valueType)))
    case Some(_) =>
  }

  override def constrain(c: Constraint): MemObject =
    new SymbolicObject(c :: constraints, valueType, Some(applyConstraint(eval.get, c, valueType)))

  override def constrain(cs: List[Constraint]): MemObject =
    new SymbolicObject(cs ++ constraints, valueType, Some(cs.foldLeft(eval.get)((cache, c) => applyConstraint(cache, c, valueType))))

}

object SymbolicObject {
  /**
   * Easily build a new value with an initial constraint attached.
   * @param c
   * @param valueType
   * @return
   */
  def fromConstraint(c: Constraint, valueType: NumericType = NumericType()): SymbolicObject = new SymbolicObject(List(c), valueType)
  def fromConstraints(cs: List[Constraint], valueType: NumericType = NumericType()): SymbolicObject = new SymbolicObject(cs, valueType)
  def unconstrained(valueType: NumericType = NumericType()): SymbolicObject = new SymbolicObject(Nil, valueType)
}