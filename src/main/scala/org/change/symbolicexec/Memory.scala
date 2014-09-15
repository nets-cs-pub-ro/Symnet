package org.change.symbolicexec

import org.change.symbolicexec._
import org.change.symbolicexec.types.{TypeUtils, NumericType}

class Memory(val mem: MemStore = Map()) {

  def resolveAllValues(s: Symbol): List[Value] = mem.getOrElse(s, Nil)

  def evalSymbolToPossibleValues(s: Symbol): Option[ValueSet] = mem.get(s) match {
    case Some(v) => Some(v.head.eval)
    case None => None
  }

  def exists(s: Symbol): Boolean = mem.contains(s)

  def resolveToCurrent(s: Symbol): Option[Value] = mem.get(s) match {
    case Some(vs) => Some(vs.head)
    case None => None
  }

  def constrainCurrent(s: Symbol, c: Constraint): Memory = resolveToCurrent(s) match {
    case None => newVal(s, TypeUtils.canonicalForSymbol(s), c)
    case Some(v) => {
      val newV = v.constrain(c)
      val vHistory = mem.getOrElse(s, Nil)
      new Memory(mem + ((s, newV :: vHistory.tail)))
    }
  }

  def constrain(s: Symbol, cs: List[Constraint]): Memory =
    cs.foldLeft(this)((m, c) => m.constrainCurrent(s, c))
  def constrain(s: Symbol, c: Constraint): Memory =
    this.constrainCurrent(s, c)

  def valid: Boolean = mem.forall(_._2.head.valid)

  def removeSymbol(s: Symbol): Memory = new Memory(mem - s)

  def newVal(s: Symbol): Memory = newVal(s, Value.unconstrained(TypeUtils.canonicalForSymbol(s)))
  def newVal(s: Symbol, t: NumericType): Memory = newVal(s, Value.unconstrained(t))
  def newVal(s: Symbol, t: NumericType, c: Constraint): Memory = newVal(s, Value.fromConstraint(c, t))
  def newVal(s: Symbol, t: NumericType, cs: List[Constraint]): Memory = newVal(s, Value.fromConstraints(cs, t))
  def newVal(s: Symbol, v: Value): Memory = new Memory(
    mem + ((s, v :: mem.getOrElse(s, Nil)))
  )

  def rewrite(s: Symbol, v: Value): Memory = newVal(s,v)

//  Taking memory snapshots
  def snapshotOf(ss: List[Symbol]): MemoryState = snapshot(mem.filter( kv => ss.contains(kv._1) ))
  def snapshotOfAll = snapshot(mem)

  private def snapshot(mem: MemStore): MemoryState = new MemoryState(mem.map( kv => (kv._1, kv._2.head.eval) ))
}

object Memory {
//TODO: I guess the list of cannonical headers should be moved to a proper location
  def withCanonical(headers: List[String] = List("IP-Src","IP-Dst","Port-Src","Port-Dst")): Memory =
    headers.foldLeft(new Memory())((m,h) => m.newVal(h))

  def withCanonocal(header: String*): Memory = withCanonocal(header:_*)
}



class MemoryState(mem: Map[Symbol, ValueSet]) {
  def stateForSymbol(s: Symbol): Option[ValueSet] = mem get s

  def supersetOf(other: MemoryState): Boolean = mem.forall( smb =>
    other.stateForSymbol(smb._1) match {
      case Some(vs) => {
        intersect(List(smb._2, vs)) == vs
      }
      case _ => true
    }
  )

  def subsetOf(other: MemoryState): Boolean = mem.forall( smb =>
    other.stateForSymbol(smb._1) match {
      case Some(vs) => intersect(List(smb._2, vs)) == smb._2
      case _ => true
    }
  )

  override def toString = mem.toString()
}

object MemoryState {
  def apply(smbc: Map[Symbol, List[Constraint]]): MemoryState = new MemoryState(
    smbc.map(smb => (smb._1, AND(smb._2).asSet()))
  )
}
