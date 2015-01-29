package org.change.improvedse

import org.change.symbolicexec._
import org.change.symbolicexec.types.{NumericType, TypeUtils}

/**
 * Created by radu on 22.01.2015.
 */
class Memory(memSpace: Map[String, List[MemObject]]) {

  /**
   * Checks if symbol s is defined.
   * @param s
   * @return
   */
  def exists(s: Symbol): Boolean = memSpace.contains(s)

  def symbolVersion(s: Symbol): Option[Int] = memSpace.get(s).map( _.length )

  def resolveAllValues(s: Symbol): Option[List[MemObject]] = memSpace.get(s)

  def evalSymbolToPossibleValues(s: Symbol): Option[ValueSet] = memSpace.get(s).flatMap { _.head.eval }

  def resolveToCurrent(s: Symbol): Option[MemObject] = memSpace.get(s).map { _.head }

  def constrainCurrent(s: Symbol, c: Constraint): Memory = resolveToCurrent(s) match {
    case None => newVal(s, TypeUtils.canonicalForSymbol(s), c)
    case Some(v) => {
      val newV = v.constrain(c)
      val vHistory = memSpace.getOrElse(s, Nil)
      new Memory(memSpace + ((s, newV :: vHistory.tail)))
    }
  }

  def constrain(s: Symbol, cs: List[Constraint]): Memory =
    cs.foldLeft(this)((m, c) => m.constrainCurrent(s, c))
  def constrain(s: Symbol, c: Constraint): Memory =
    this.constrainCurrent(s, c)

  def valid: Boolean = memSpace.forall(_._2.head.valid)

  def removeSymbol(s: Symbol): Memory = new Memory(memSpace - s)

  def newVal(s: Symbol): Memory = newVal(s, SymbolicObject.unconstrained(TypeUtils.canonicalForSymbol(s)))
  def newVal(s: Symbol, t: NumericType): Memory = newVal(s, SymbolicObject.unconstrained(t))
  def newVal(s: Symbol, t: NumericType, c: Constraint): Memory = newVal(s, SymbolicObject.fromConstraint(c, t))
  def newVal(s: Symbol, t: NumericType, cs: List[Constraint]): Memory = newVal(s, SymbolicObject.fromConstraints(cs, t))
  def newVal(s: Symbol, v: MemObject): Memory = new Memory(
    memSpace + ((s, v :: memSpace.getOrElse(s, Nil)))
  )

  def rewrite(s: Symbol, v: MemObject): Memory = newVal(s,v)

  //  Taking memory snapshots: Later
  //  def snapshotOf(ss: List[Symbol]): MemoryState = snapshot(mem.filter( kv => ss.contains(kv._1) ))
  //  def snapshotOfAll = snapshot(mem)
  //
  //  private def snapshot(mem: MemStore): MemoryState = new MemoryState(mem.map( kv => (kv._1, kv._2.head.eval) ))
}
