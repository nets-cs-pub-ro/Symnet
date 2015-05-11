package org.change.v2.analysis.expression.concrete.nonprimitive

import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.memory.Value
import z3.scala.{Z3Solver, Z3AST}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger to symnet.radustoe@spamgourmet.com
 */
case class Ref(what: Value) extends Expression {
  override def toZ3(solver: Option[Z3Solver] = None): (Z3AST, Option[Z3Solver]) = what.toZ3(solver)
}
