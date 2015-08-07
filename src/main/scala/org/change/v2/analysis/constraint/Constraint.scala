package org.change.v2.analysis.constraint

import org.change.v2.analysis.types._
import org.change.v2.analysis.z3.Z3Util
import org.change.v2.interval._
import org.change.v2.interval.IntervalOps._
import z3.scala.{Z3Solver, Z3AST}

trait Constraint {
  /**
   * Materialize a constraint as the set of possible values.
   *
   * An empty list result marks the constraint as unsatisfiable.
   *
   * @param valueType For bound checks.
   */
  def asSet(valueType: NumericType = LongType): List[Interval] = Nil

  /**
   *
   * @param subject On what Z3 AST the constraint applies to.
   * @return The new AST incorporating the current constraint.
   */
  def z3Constrain(subject: Z3AST): Z3AST
}

object Constraint {
  def applyConstraint(s: ValueSet, c: Constraint,
                      t: NumericType = LongType): ValueSet =
    intersect(List(s, c.asSet(t)))

  def applyConstraints(s: ValueSet, cts: List[Constraint],
                       t: NumericType = LongType): ValueSet =
    intersect(s :: cts.map(_.asSet()))
}

object No extends Constraint {
  override def asSet(valueType: NumericType): List[(Long, Long)] = {
    List((valueType.min, valueType.max))
  }

  override def z3Constrain(subject: Z3AST): Z3AST = subject
}

case class LT(v: Long) extends Constraint {
  override def asSet(valueType: NumericType): List[(Long, Long)] = {
    List((valueType.min, Math.min(v-1, valueType.max)))
  }

  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkLT(ast, Z3Util.z3Context.mkInt(v.asInstanceOf[Int], Z3Util.defaultSort))
}

case class LTE(v: Long) extends Constraint {
  override def asSet(valueType: NumericType): List[(Long, Long)] = {
    List((valueType.min, Math.min(v, valueType.max)))
  }

  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkLE(ast, Z3Util.z3Context.mkInt(v.asInstanceOf[Int], Z3Util.defaultSort))
}

case class GT(v: Long) extends Constraint {
  override def asSet(valueType: NumericType): List[(Long, Long)] = {
    List((Math.max(v+1, valueType.min), valueType.max))
  }

  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkGT(ast, Z3Util.z3Context.mkInt(v.asInstanceOf[Int], Z3Util.defaultSort))
}

case class GTE(v: Long) extends Constraint{
  override def asSet(valueType: NumericType): List[(Long, Long)] = {
    List((Math.max(v, valueType.min), valueType.max))
  }

  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkGE(ast, Z3Util.z3Context.mkInt(v.asInstanceOf[Int], Z3Util.defaultSort))
}

case class E(v: Long) extends Constraint{
  override def asSet(valueType: NumericType): List[(Long, Long)] =
    if (valueType.min <= v && v <= valueType.max) List((v,v))
    else Nil

  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkEq(ast, Z3Util.z3Context.mkInt(v.asInstanceOf[Int], Z3Util.defaultSort))
}

case class Range(v1: Long, v2: Long) extends Constraint {
  override def asSet(valueType: NumericType): List[(Long, Long)] = {
    List((Math.max(v1, valueType.min), Math.min(v2, valueType.max)))
  }

  override def z3Constrain(ast: Z3AST): Z3AST =
    Z3Util.z3Context.mkAnd(
      Z3Util.z3Context.mkGE(ast, Z3Util.z3Context.mkInt(v1.asInstanceOf[Int], Z3Util.defaultSort)),
      Z3Util.z3Context.mkLE(ast, Z3Util.z3Context.mkInt(v2.asInstanceOf[Int], Z3Util.defaultSort)))
}

case class OR(constraints: List[Constraint]) extends Constraint {
  override def asSet(valueType: NumericType = LongType): List[Interval] =
    union(constraints.map(_.asSet(valueType)))

  override def z3Constrain(subject: Z3AST): Z3AST =
    Z3Util.z3Context.mkOr(constraints.map(_.z3Constrain(subject)):_*)

  override def toString = s"|($constraints)"
}

case class AND(constraints: List[Constraint]) extends Constraint {
  override def asSet(valueType: NumericType = LongType): List[Interval] =
    intersect(constraints.map(_.asSet(valueType)))

  override def z3Constrain(subject: Z3AST): Z3AST =
    Z3Util.z3Context.mkAnd(constraints.map(_.z3Constrain(subject)):_*)
  override def toString = s"&($constraints)"
}

case class NOT(constraint: Constraint) extends Constraint {
  override def asSet(valueType: NumericType = LongType): List[Interval] =
    complement(constraint.asSet(valueType))

  override def z3Constrain(subject: Z3AST): Z3AST =
    Z3Util.z3Context.mkNot(constraint.z3Constrain(subject))
  override def toString = s"~($constraint)"
}

