package org.change.v2.analysis.memory
import org.change.v2.analysis.types.{NumericType, TypeUtils, Type}
import org.change.v2.interval.ValueSet
import org.change.v2.util.codeabstractions._

import scala.collection.mutable.{Map => MutableMap}

/**
*  Author: Radu Stoenescu
*  Don't be a stranger to symnet.radustoe@spamgourmet.com
*/
class MemorySpace(val symbolSpace: MutableMap[String, MemorySymbol] = MutableMap()) {

  private var age = -1

  /**
   * The memory is initially void, so there has to be a way to bring a symbol to existence.
   *
   * COMEBACK: Would be nice to access previous args in the expression for default new ones.
   */
  private def createSymbol(symbolId: String, symbolType: Option[NumericType] = None): MemorySpace =
    selfMutate { m => m.symbolSpace.get(symbolId) match {
        case None => m.symbolSpace += ((symbolId, new MemorySymbol(
          symbolType.getOrElse(TypeUtils.canonicalForSymbol(symbolId)))))
        case _ => // NoOp, a symbol with the same id exists, nothing to do.
      }
    }

  private def selfUpdate(f: (MemorySpace => Unit)): MemorySpace = {
    mutateAndReturn(this)(f)
  }

  private def selfMutate(f: (MemorySpace => Unit)): MemorySpace = {
    age += 1
    selfUpdate(f)
  }
}
