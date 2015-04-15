package org.change.v2.analysis.memory

import org.change.v2.analysis.constraint.Constraint
import org.change.v2.analysis.expression.Expression
import org.change.v2.analysis.z3.Z3Able
import z3.scala.Z3AST

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Value(e: Expression, cts: List[Constraint] = Nil) extends Z3Able {
  override lazy val toZ3: Z3AST = e.toZ3
}
