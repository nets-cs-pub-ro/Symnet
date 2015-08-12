package org.change.v2.analysis.expression.concrete.nonprimitive

import org.change.v2.analysis.expression.abst.{FloatingExpression, Expression}
import org.change.v2.analysis.memory.Value
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.z3.Z3Util
import z3.scala.{Z3Solver, Z3AST}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Minus(a: Value, b: Value) extends Expression {
  override def toZ3(solver: Option[Z3Solver] = None): (Z3AST, Option[Z3Solver]) = {
    val (aAst, aSolver) = a.toZ3(solver)
    val (bAst, bSolver) = b.toZ3(aSolver)

    (Z3Util.z3Context.mkSub(aAst, bAst), bSolver)
  }

  override def toString = s"(${a.e} - ${b.e})"
}

case class :-:(left: FloatingExpression, right: FloatingExpression) extends FloatingExpression {
  /**
   * A floating expression may include unbounded references (e.g. symbol ids)
   *
   * Given a context (the state) it can produce an evaluable expression.
   * @param s
   * @return
   */
  override def instantiate(s: State): Either[Expression, String] = {
    (left instantiate s) match {
      case Left(e1) => (right instantiate s) match {
        case Left(e2) => Left(Minus(Value(e1), Value(e2)))
        case cause2@Right(_) => cause2
      }
      case cause1@Right(_) => cause1
    }
  }
}
