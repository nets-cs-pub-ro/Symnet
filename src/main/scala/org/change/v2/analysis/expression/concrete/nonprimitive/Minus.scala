package org.change.v2.analysis.expression.concrete.nonprimitive

import org.change.v2.analysis.memory.Value
import org.change.v2.analysis.expression.Expression
import org.change.v2.analysis.z3.Z3Util
import z3.scala.Z3AST

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Minus(a: Value, b: Value) extends Expression {
  override lazy val toZ3: Z3AST = Z3Util.z3Context.mkSub(a.toZ3, b.toZ3)
}
