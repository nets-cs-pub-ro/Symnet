package org.change.symbolicexec

import org.change.symbolicexec.types.{TypeUtils, NumericType}

class Memory(val mem: MemStore = Map()) {

  def eval(s: Symbol): Option[ValueSet] = mem.get(s) match {
    case Some(v) => Some(v.head.eval)
    case None => None
  }

  def exists(s: Symbol): Boolean = mem.contains(s)

  def resolve(s: Symbol): Option[Value] = mem.get(s) match {
    case Some(vs) => Some(vs.head)
    case None => None
  }

  def constrain(s: Symbol, c: Constraint): Memory = resolve(s) match {
    case None => newVal(s, TypeUtils.canonicalForSymbol(s), c)
    case Some(v) => {
      val newV = v.constrain(c)
      val vHistory = mem.getOrElse(s, Nil)
      new Memory(mem + ((s, newV :: vHistory.tail)))
    }
  }

  def constrain(s: Symbol, cs: List[Constraint]): Memory =
    cs.foldLeft(this)((m, c) => m.constrain(s, c))

  def removeSymbol(s: Symbol): Memory = new Memory(mem - s)

  def newVal(s: Symbol, t: NumericType): Memory = new Memory(
    mem + ((s, Value.unconstrained(t) :: mem.getOrElse(s, Nil)))
  )

  def newVal(s: Symbol, t: NumericType, c: Constraint): Memory = new Memory(
    mem + ((s, Value.fromConstraint(c, t) :: mem.getOrElse(s, Nil)))
  )

  def newVal(s: Symbol, t: NumericType, cs: List[Constraint]): Memory = new Memory(
    mem + ((s, Value(cs, t) :: mem.getOrElse(s, Nil)))
  )

  def valid: Boolean = mem.exists(! _._2.head.valid)
}
