package org.change.improvedse

import org.change.symbolicexec._
import org.change.symbolicexec.types.NumericType

/**
 * Created by radu on 22.01.2015.
 */
class LiteralObject(val value: Long,
                    constraints: List[Constraint] = Nil,
                    valueType: NumericType = NumericType(),
                    cache: Option[List[Interval]] = None) extends MemObject(constraints, valueType, cache) {

  //  If no cache to start with, compute it, eagerly so far
  evalCache match {
    case None => evalCache = Some((E(value) :: constraints).foldLeft(valueType.admissibleSet)((s, c) => applyConstraint(s,c,valueType)))
    case Some(_) =>
  }

  override def forceEval: List[Interval] = {
    /**
     * Compute and store
     */
    val vs = (E(value) :: constraints).foldLeft(valueType.admissibleSet)((s, c) => applyConstraint(s,c,valueType))
    evalCache = Some(vs)
    vs
  }

  override def constrain(c: Constraint): MemObject = new LiteralObject(value, c :: constraints, valueType)

  override def constrain(cs: List[Constraint]): MemObject = new LiteralObject(value, cs ++ constraints, valueType)
}
