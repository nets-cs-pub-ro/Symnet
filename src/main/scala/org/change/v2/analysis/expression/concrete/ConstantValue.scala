package org.change.v2.analysis.expression.concrete

import org.change.v2.analysis.expression.Expression
import org.change.v2.analysis.z3.Z3Util
import z3.scala.Z3AST

/**
 * Created by radu on 3/24/15.
 */
case class ConstantValue(value: Long) extends Expression {
  override lazy val toZ3: Z3AST = Z3Util.z3Context.mkInt(value.asInstanceOf[Int], Z3Util.intSort)
}
