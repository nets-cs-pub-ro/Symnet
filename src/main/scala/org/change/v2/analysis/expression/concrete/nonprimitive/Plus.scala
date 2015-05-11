package org.change.v2.analysis.expression.concrete.nonprimitive

import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.memory.Value
import org.change.v2.analysis.z3.Z3Util
import z3.scala.{Z3Solver, Z3AST}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Plus(a: Value, b: Value) extends Expression {
  override def toZ3(solver: Option[Z3Solver] = None): (Z3AST, Option[Z3Solver]) = {
    val (aAst, aSolver) = a.toZ3(solver)
    val (bAst, bSolver) = b.toZ3(aSolver)

    (Z3Util.z3Context.mkAdd(aAst, bAst), bSolver)
  }
}
