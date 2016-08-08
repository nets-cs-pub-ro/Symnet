package org.change.v3.sefl.expression.concrete

import org.change.v3.sefl.expression.abst.{Expression, FloatingExpression}
import org.change.v2.analysis.memory.{Intable, State, TagExp, Value}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger to symnet.radustoe@spamgourmet.com
 */
case class Reference(what: Value) extends Expression(what.e.id) {
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
