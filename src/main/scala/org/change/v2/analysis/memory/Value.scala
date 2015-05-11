package org.change.v2.analysis.memory

import org.change.v2.analysis.constraint.Constraint
import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.z3.Z3Able
import z3.scala.{Z3Solver, Z3AST}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Value(e: Expression, cts: List[Constraint] = Nil) extends Z3Able {

  override def toZ3(solver: Option[Z3Solver] = None): (Z3AST, Option[Z3Solver]) = {
    val (ast, afterAstBuildSolver) = e.toZ3(solver)

    for {
      s <- afterAstBuildSolver
      c <- cts
    } {
      s.assertCnstr(c.z3Constrain(ast))
    }

    (ast, afterAstBuildSolver)
  }
}
