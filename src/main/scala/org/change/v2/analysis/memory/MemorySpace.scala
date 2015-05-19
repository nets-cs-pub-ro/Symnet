package org.change.v2.analysis.memory

import org.change.v2.analysis.constraint.Constraint
import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.expression.concrete.SymbolicValue
import org.change.v2.analysis.types.{NumericType, TypeUtils, Type}
import org.change.v2.analysis.z3.Z3Util
import org.change.v2.interval.ValueSet
import org.change.v2.util.codeabstractions._

import scala.collection.mutable.{Map => MutableMap}

/**
*  Author: Radu Stoenescu
*  Don't be a stranger to symnet.radustoe@spamgourmet.com
*/
class MemorySpace(val symbolSpace: MutableMap[String, MemorySymbol] = MutableMap()) {

  /**
   * Introsection PUBLIC API
   */

  def version: Int = age

  def symbolSSAVersion(id: String): Int = symbolSpace.get(id).map(_.valueStack.length).getOrElse(0)

  def symbolIsDefined(id: String) = { symbolSSAVersion(id) > 0 }
  def symbolIsDefinedAndVisible(id: String) = { symbolIsDefined(id) && ! symbolSpace(id).hidden }

  /**
   * Operational PUBLIC API
   */
  def REMOVE(id: String): Option[MemorySpace] = { Some(remove(id)) }

  /**
   * Rewrite a symbol to a new expression.
   *
   * @param id
   * @param exp
   * @return
   */
  def REWRITE(id: String, exp: Expression): Option[MemorySpace] = { Some(rewrite(id, exp)) }

  /**
   * If defined and visible returns the value currently bound to a given symbol id.
   * @param id
   * @return
   */
  def GET(id:String): Option[Value] = if (symbolIsDefinedAndVisible(id))
      Some(symbolSpace(id).currentValueOnly)
    else
      None

  /**
   * Force get value of symbol.
   * If the symbol is not defined, it is created and bound to a canonical value.
   *
   * @param id
   * @return
   */
  def FGET(id: String): Value = if (symbolIsDefinedAndVisible(id))
      symbolSpace(id).currentValueOnly
    else
      createAndShow(id).FGET(id)

  def CONSTRAIN(id: String, c: Constraint): Option[MemorySpace] = symbolSpace.get(id).map(smb => {
      val newSmb = smb.constrain(c)
      new MemorySpace(symbolSpace + ((id, newSmb)))
    }
  )

  private var age = -1

  /**
   * The memory is initially void, so there has to be a way to bring a symbol to existence.
   *
   * The new symbol is initially hidden because there is no value defined in the SSA stack.
   * Moreover, this is why it uses update instead of mutate.
   *
   * This is method performs the trick of presenting an infinite memory backed by an on-demand
   * allocation system.
   *
   * COMEBACK: Would be nice to access previous args in the expression for default new ones.
   */
  private def createSymbol(symbolId: String, symbolType: Option[NumericType] = None): MemorySpace =
    selfUpdate { m => m.symbolSpace.get(symbolId) match {
        case None => m.symbolSpace += ((symbolId, new MemorySymbol(
          symbolType.getOrElse(TypeUtils.canonicalForSymbol(symbolId)))))
        case _ => // NoOp, a symbol with the same id exists, nothing to do.
      }
    }

  /**
   * ATTENTION: May not be needed
   *
   * @param symbolId
   * @param symbolType
   * @return
   */
  private def createAndShow(symbolId: String, symbolType: Option[NumericType] = None): MemorySpace =
    createSymbol(symbolId, symbolType).show(symbolId)

  /**
   * Pushes a new expression on the SSA stack of a symbol.
   * @param symbolId
   * @param exp
   * @return
   */
  def rewrite(symbolId: String, exp: Expression) = createSymbol(symbolId).selfMutate {
    _.symbolSpace(symbolId).rewrite(exp)
  }

  def duplicateExpression(toSymbol: String, fromSymbol: String) = ???

  /**
   * If symbol is defined and visible, it is hidden, otherwise, NoOp
   * @param id
   * @return
   */
  private def remove(id: String) = if (symbolSpace.contains(id) && ! symbolSpace(id).hidden)
    selfMutate(_.symbolSpace(id).hide)
  else
    this

  /**
   * Make a hidden symbol visible again
   * @param id
   * @return
   */
  private def show(id: String) = if (symbolSpace.contains(id))
    selfMutate(_.symbolSpace(id).show)
  else
    this

  private def selfUpdate(f: (MemorySpace => Unit)): MemorySpace = {
    mutateAndReturn(this)(f)
  }

  private def selfMutate(f: (MemorySpace => Unit)): MemorySpace = {
    age += 1
    selfUpdate(f)
  }

  /**
   * ATTENTION: Incomplete
   * @return
   */
  override def toString = symbolSpace.toString()

  def valid: Boolean = isZ3Valid
  def isZ3Valid: Boolean = {
    val solver = Z3Util.solver
    val loadedSolver = symbolSpace.values.foldLeft(solver) { (slv, ms) =>
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
  def cleanWithSymolics(symbols: List[String]) = symbols.foldLeft(clean)((mem, s) => mem.REWRITE(s, SymbolicValue()).get)
}
