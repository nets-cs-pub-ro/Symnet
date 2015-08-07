package org.change.v2.analysis.constraint

import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.z3.Z3Util
import z3.scala.Z3AST

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class LT_E(e: Expression) extends Constraint {
  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkLT(ast, e.toZ3()._1)
  override def toString = s"<($e)"
}

case class LTE_E(e: Expression) extends Constraint {
  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkLE(ast, e.toZ3()._1)
  override def toString = s"<=($e)"
}
case class GTE_E(e: Expression)extends Constraint {
  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkGE(ast, e.toZ3()._1)
  override def toString = s">=($e)"
}
case class GT_E(e: Expression)extends Constraint {
  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkGT(ast, e.toZ3()._1)
  override def toString = s">($e)"
}
case class EQ_E(e: Expression)extends Constraint {
  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkEq(ast, e.toZ3()._1)
  override def toString = s"==($e)"
}
