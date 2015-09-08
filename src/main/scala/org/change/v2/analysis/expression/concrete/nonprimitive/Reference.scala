package org.change.v2.analysis.expression.concrete.nonprimitive

import org.change.v2.analysis.expression.abst.{FloatingExpression, Expression}
import org.change.v2.analysis.memory.Value
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.processingmodels.instructions.Fail
import z3.scala.{Z3Solver, Z3AST}
import org.change.v2.analysis.memory.{TagExp, Intable}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger to symnet.radustoe@spamgourmet.com
 */
case class Reference(what: Value) extends Expression(what.e.id) {
  override def toZ3(solver: Option[Z3Solver] = None): (Z3AST, Option[Z3Solver]) = what.toZ3(solver)
  override def toString = what.e.toString
}

/**
 * A floating expression of this type produces a reference to a value. If the symbol referred by the id
 * is not found, an error is produced.
 * @param id
 */
case class Symbol(id: String) extends FloatingExpression {
  /**
   * A floating expression may include unbounded references (e.g. symbol ids)
   *
   * Given a context (the state) it can produce an evaluable expression.
   * @param s
   * @return
   */
  override def instantiate(s: State): Either[Expression, String] = {
    s.memory.eval(id) match {
      case Some(v) => Left(Reference(v))
      case None => Right(s"Cannot resolve reference to symbol: $id")
    }
  }
}

case class Address(a: Intable) extends FloatingExpression {
  /**
   * A floating expression may include unbounded references (e.g. symbol ids)
   *
   * Given a context (the state) it can produce an evaluable expression.
   * @param s
   * @return
   */
  override def instantiate(s: State): Either[Expression, String] = a(s) match {
    case Some(int) => s.memory.eval(int) match {
      case Some(v) => Left(Reference(v))
      case None => Right(s"Cannot resolve reference to $int")
    }
    case None => Right(TagExp.brokenTagExpErrorMessage)
  }
}

object :@ {
  def apply(id: String): FloatingExpression = Symbol(id)
  def apply(a: Intable): FloatingExpression = Address(a)
}
