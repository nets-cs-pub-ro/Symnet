package org.change.v2.analysis.processingmodels

import org.change.v2.analysis.memory.MemorySpace

import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.expression.concrete.SymbolicValue


/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class State(memory: MemorySpace = MemorySpace.clean,
                 history: List[LocationId] = Nil,
                 errorCause: Option[ErrorCause] = None) {
  def location: LocationId = history.head
  def forwardTo(locationId: LocationId): State = State(memory, locationId :: history, errorCause)
  def status = errorCause.getOrElse("OK")
  override def toString = s"Path ($status) {\n$memory\n} End Of Path Desc"
}

object State {
 def bigBang: State = {
   val init = State(MemorySpace.clean)
   InstructionBlock (
     Assign(IPSrc, SymbolicValue()),
     Assign(IPDst, SymbolicValue()),
     Assign(PortSrc, SymbolicValue()),
     Assign(PortDst, SymbolicValue()),
     Assign(Proto, SymbolicValue())
   )(init)._1.head
 }

}
