package org.change.v2.analysis.memory

import org.change.v2.analysis.constraint._
import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.expression.concrete.SymbolicValue
import org.change.v2.analysis.types.{LongType, NumericType, TypeUtils, Type}
import org.change.v2.analysis.z3.Z3Util
import org.change.v2.interval.{IntervalOps, ValueSet}
import org.change.v2.util.codeabstractions._

import scala.collection.mutable.{Map => MutableMap}

/**
*  Author: Radu Stoenescu
*  Don't be a stranger to symnet.radustoe@spamgourmet.com
*/
case class MemorySpace(val symbols: Map[String, MemoryObject] = Map.empty,
                       val rawObjects: Map[Int, MemoryObject] = Map.empty,
                       val memTags: Map[String, Int] = Map.empty) {

  private def resolveBy[K](id: K, m: Map[K, MemoryObject]): Option[Value] =
    m.get(id).flatMap(_.value)

  def Tag(name: String, value: Int): Option[MemorySpace] = Some(MemorySpace(symbols, rawObjects, memTags + (name -> value)))

  /**
   * Get the currently visible value associated with a symbol.
   * @param id symol name
   * @return the value or none
   */
  def eval(id: String): Option[Value] = resolveBy(id, symbols)
  def eval(a: Int): Option[Value] = resolveBy(a, rawObjects)

  def canRead(a: Int): Boolean = resolveBy(a, rawObjects).isDefined

  def isAllocated(a: Int): Boolean = rawObjects.contains(a)

  private def doesNotOverlap(a: Int, size: Int): Boolean = {
    (! rawObjects.contains(a)) &&
      rawObjects.forall(kv => ! IntervalOps.intervalIntersectionIsInterval(a, size, kv._1, kv._2.size))
  }

  def canModify(a: Int, size: Int): Boolean = doesNotOverlap(a, size) ||
    (rawObjects.contains(a) && rawObjects(a).size == size)

  def canModifyExisting(a: Int, size: Int): Boolean = rawObjects.contains(a) && rawObjects(a).size == size

  /**
   * Checks if a given symbol is assigned to a value.
   * @param id
   * @return
   */
  def symbolIsAssigned(id: String): Boolean = { eval(id).isDefined }
  def symbolIsDefined(id: String): Boolean = { symbols.contains(id) }

  /**
   * Allocates a new empty stack for a given symbol.
   * @param id
   * @return
   */
  def Allocate(id: String): Option[MemorySpace] =
    Some(MemorySpace(
      symbols + ( id -> (if (! symbolIsDefined(id)) MemoryObject() else symbols(id).allocateNewStack)),
      rawObjects,
      memTags
    ))

  def Allocate(a: Int, size: Int): Option[MemorySpace] = if (canModifyExisting(a, size))
    Some(MemorySpace(
      symbols,
      rawObjects + ( a -> rawObjects(a).allocateNewStack),
      memTags
    ))
  else if (canModify(a, size))
    Some(MemorySpace(
      symbols,
      rawObjects + ( a -> MemoryObject(size = size)),
      memTags
    ))
  else None

  /**
   * Destroys the newest stack assigned to a value.
   * @param id
   * @return
   */
  def Deallocate(id: String): Option[MemorySpace] = symbols.get(id).flatMap(_.deallocateStack).map( o =>
    MemorySpace(
      if (o.isVoid) symbols - id else symbols + (id -> o),
      rawObjects,
      memTags
    )
  )

  def Deallocate(a: Int, size: Int): Option[MemorySpace] = if (canModifyExisting(a, size))
    rawObjects.get(a).flatMap(_.deallocateStack).map( o =>
      MemorySpace(
        symbols,
        if (o.isVoid) rawObjects - a else rawObjects + (a -> o),
        memTags
      )
    )
  else
    None

  /**
   * Rewrite a symbol to a new expression.
   *
   * @param id
   * @param exp
   * @return
   */
  def Assign(id: String, exp: Expression, eType: NumericType): Option[MemorySpace] = { assignNewValue(id, exp, eType) }
  def Assign(id: String, exp: Expression): Option[MemorySpace] = Assign(id, exp, TypeUtils.canonicalForSymbol(id))
  def Assign(a: Int, exp: Expression): Option[MemorySpace] = if (isAllocated(a))
    {
      val nm = Some(MemorySpace(
        symbols,
        rawObjects + (a -> rawObjects(a).addValue(Value(exp))),
        memTags
      ))
      nm
    }

  else
    None

  def Constrain(id: String, c: Constraint): Option[MemorySpace] = eval(id).flatMap(smb => {
    val newSmb = smb.constrain(c)
    val newMem = replaceValue(id, newSmb).get

    val subject = newMem.eval(id).get

    c match {
      case EQ_E(someE) if someE.id == subject.e.id => Some(newMem)
      case GT_E(someE) if someE.id == subject.e.id => None
      case GTE_E(someE) if someE.id == subject.e.id => Some(newMem)
      case LT_E(someE) if someE.id == subject.e.id => None
      case LTE_E(someE) if someE.id == subject.e.id => Some(newMem)
      case _ => memoryToOption(newMem)
    }
  })

  def Constrain(a: Int, c: Constraint): Option[MemorySpace] = eval(a).flatMap(smb => {
    val newSmb = smb.constrain(c)
    val newMem = replaceValue(a, newSmb).get

    val subject = newMem.eval(a).get

    c match {
      case EQ_E(someE) if someE.id == subject.e.id => Some(newMem)
      case GT_E(someE) if someE.id == subject.e.id => None
      case GTE_E(someE) if someE.id == subject.e.id => Some(newMem)
      case LT_E(someE) if someE.id == subject.e.id => None
      case LTE_E(someE) if someE.id == subject.e.id => Some(newMem)
      case _ => memoryToOption(newMem)
    }
  })

  private[this] def memoryToOption(m: MemorySpace): Option[MemorySpace] =
    if (m.isZ3Valid)
      Some(m)
    else
      None

  def replaceValue(id: String, v: Value): Option[MemorySpace] = symbols.get(id).flatMap(_.replaceValue(v)).map(
    mo => MemorySpace(
      symbols + (id -> mo),
      rawObjects,
      memTags
    )
  )

  def replaceValue(id: Int, v: Value): Option[MemorySpace] = rawObjects.get(id).flatMap(_.replaceValue(v)).map(
    mo => MemorySpace(
      symbols,
      rawObjects + (id -> mo),
      memTags
    )
  )

  /**
   * Pushes a new expression on the newest SSA stack of a symbol.
   * @param id
   * @param exp
   * @return
   */
  def assignNewValue(id: String, exp: Expression, eType: NumericType): Option[MemorySpace] = assignNewValue(id, Value(exp, eType))

  def assignNewValue(id: String, v: Value): Option[MemorySpace] =
    Some(MemorySpace(
      symbols + (id -> (if (symbolIsDefined(id)) symbols(id).addValue(v) else MemoryObject().addValue(v))),
      rawObjects,
      memTags
    ))

  /**
   *
   * TODO: Incomplete
   * @return
   */
  override def toString = "Tags:" + memTags.mkString("\n") +
    "Memory values:\n" +
    symbols.map(kv => kv._1 -> ("Crt:" + kv._2.value, "Initital: " + kv._2.initialValue)).mkString("\n") +
    rawObjects.map(kv => kv._1 -> ("Crt:" + kv._2.value, "Initital: " + kv._2.initialValue)).mkString("\n") + "\n"

  def valid: Boolean = isZ3Valid
  def isZ3Valid: Boolean = {
    val solver = Z3Util.solver
    val loadedSolver = (symbols.values ++ rawObjects.values).foldLeft(solver) { (slv, mo) =>
      mo.value match {
        case Some(v) => v.toZ3(Some(slv))._2.get
        case _ => slv
      }
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
