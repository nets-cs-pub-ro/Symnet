package org.change.v2.analysis.expression.concrete.nonprimitive

import org.change.v2.analysis.expression.Expression
import org.change.v2.analysis.memory.Value
import z3.scala.Z3AST

/**
 * Author: Radu Stoenescu
 * Don't be a stranger to symnet.radustoe@spamgourmet.com
 */
case class Ref(what: Value) extends Expression {
  override def toZ3: Z3AST = ???
}
