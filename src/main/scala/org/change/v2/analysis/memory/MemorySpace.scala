package org.change.v2.analysis.memory
import org.change.v2.analysis.types.{NumericType, TypeUtils, Type}
import org.change.v2.interval.ValueSet
import org.change.v2.util.codeabstractions._
import org.change.v2.analysis.expression.Expression

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

  def REWRITE(id: String, exp: Expression): Option[MemorySpace] = { Some(rewrite(id, exp)) }


//  def rewrite

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

  private def selfUpdate(f: (MemorySpace => Unit)): MemorySpace = {
    mutateAndReturn(this)(f)
  }

  private def selfMutate(f: (MemorySpace => Unit)): MemorySpace = {
    age += 1
    selfUpdate(f)
  }
}
