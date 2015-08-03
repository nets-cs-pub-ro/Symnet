package org.change.v2.analysis.memory

import org.change.v2.analysis.constraint.Constraint
import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.expression.concrete.SymbolicValue
import org.change.v2.analysis.types.{LongType, NumericType, TypeUtils, Type}
import org.change.v2.analysis.z3.Z3Util
import org.change.v2.interval.ValueSet
import org.change.v2.util.codeabstractions._

import scala.collection.mutable.{Map => MutableMap}

/**
*  Author: Radu Stoenescu
*  Don't be a stranger to symnet.radustoe@spamgourmet.com
*/
case class MemorySpace(val symbols: Map[String, List[ValueStack]] = Map()) {

  /**
   * Get the currently visible value associated with a symbol.
   * @param id symol name
   * @return the value or none
   */
  def eval(id: String): Option[Value] = symbols.get(id).flatMap( headOrNone(_) ).flatMap( _.value )

  /**
   * Checks if a given symbol is assigned to a value.
   * @param id
   * @return
   */
  def symbolIsAssigned(id: String): Boolean = { optionToBoolean(eval(id)) }

  /**
   * Allocates a new empty stack for a given symbol.
   * @param id
   * @return
   */
  def Allocate(id: String): Option[MemorySpace] = Some(
    MemorySpace(addToMapping(symbols, id, ValueStack.empty)))

  /**
   * Destroys the newest stack assigned to a value.
   * @param id
   * @return
   */
  def Deallocate(id: String): Option[MemorySpace] = symbols.get(id).flatMap { _ match {
    case _ :: olderStacks => Some(MemorySpace(symbols + (id -> olderStacks)))
    case _ => None
  }}

  /**
   * Rewrite a symbol to a new expression.
   *
   * @param id
   * @param exp
   * @return
   */
  def Assign(id: String, exp: Expression, eType: NumericType): Option[MemorySpace] = { assignNewValue(id, exp, eType) }
  def Assign(id: String, exp: Expression): Option[MemorySpace] = Assign(id, exp, TypeUtils.canonicalForSymbol(id))

  /**
   * Checks if the two symbols refer the same value.
   * @param idA Symbol A.
   * @param idB Symbol B.
   * @return
   */
  def Same(idA: String, idB: String): Option[MemorySpace] =
    for {
      vA <- eval(idA)
      vB <- eval(idB)
      if (vA.e.id == vB.e.id)
    } yield (this)

  def Constrain(id: String, c: Constraint): Option[MemorySpace] = eval(id).flatMap(smb => {
      val newSmb = smb.constrain(c)
      val newMem = replaceValue(id, newSmb).get
      if (newMem.isZ3Valid)
        Some(newMem)
      else
        None
    }
  )

  def replaceValue(id: String, v: Value): Option[MemorySpace] = {
    symbols.get(id) match {
      case Some(stacks) => {
        val latestStack = stacks.head
        val newLatestStack = latestStack.replaceLatestValue(v)
        val newStacks = replaceHead(stacks, newLatestStack)
        Some(MemorySpace(symbols + (id -> newStacks)))
      }
      case None => None
    }
  }

  /**
   * Makes the 'where' symbol refer the same value as 'what' symbol
   *
   * @param where
   * @param what
   * @return
   */
  def Duplicate(where: String, what: String): Option[MemorySpace] = eval(what).flatMap( v => Assign(where, v.e))

  /**
   * Pushes a new expression on the newest SSA stack of a symbol.
   * @param id
   * @param exp
   * @return
   */
  def assignNewValue(id: String, exp: Expression, eType: NumericType): Option[MemorySpace] = assignNewValue(id, Value(exp, eType))

  def assignNewValue(id: String, v: Value): Option[MemorySpace] = symbols.get(id) match {
    case Some(sl) => Some(MemorySpace(symbols + (id -> replaceHead(sl, sl.head.addDefinition(v)))))
    case None => Allocate(id).flatMap(_.assignNewValue(id, v))
  }

  /**
   * TODO: Incomplete
   * @return
   */
  override def toString = symbols.toString()

  def valid: Boolean = isZ3Valid
  def isZ3Valid: Boolean = {
    val solver = Z3Util.solver
    val loadedSolver = symbols.values.map(_.head).foldLeft(solver) { (slv, ms) =>
      ms.currentValueOnly.toZ3(Some(slv))._2.get
    }

    loadedSolver.check().get
  }
}

object MemorySpace {
  /**
   * Empty memory.
   * @return
   */
  def clean: MemorySpace = new MemorySpace()

  /**
   * ATTENTION: Remove the ugly get and make a hl func for this
   * @param symbols What symbols should the memory contain initially.
   * @return
   */
  def cleanWithSymolics(symbols: List[String]) = symbols.foldLeft(clean)((mem, s) => mem.Assign(s, SymbolicValue()).get)
}
