package org.change.v2.analysis.processingmodels

import org.change.v2.analysis.memory.MemorySpace

import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.expression.concrete.SymbolicValue
import org.change.v2.analysis.memory.TagExp._
import org.change.v2.analysis.memory.Tag


/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class State(memory: MemorySpace = MemorySpace.clean,
                 history: List[LocationId] = Nil,
                 errorCause: Option[ErrorCause] = None,
                 instructionHistory: List[Instruction] = Nil) {
  def location: LocationId = history.head
  def forwardTo(locationId: LocationId): State = State(memory, locationId :: history, errorCause, instructionHistory)
  def status = errorCause.getOrElse("OK")
  override def toString = s"Path ($status) {\n$memory\n} End Of Path Desc"
  def addInstructionToHistory(i: Instruction) = State(memory, history, errorCause, i :: instructionHistory)
}

object State {
 def bigBang: State = {
   val bigBang = State(MemorySpace.clean)

   val afterBigBang = InstructionBlock (
     Assign(IPSrcString, SymbolicValue()),
     Assign(IPDstString, SymbolicValue()),
     Assign(PortSrcString, SymbolicValue()),
     Assign(PortDstString, SymbolicValue()),
     Assign(L4ProtoString, SymbolicValue()),
     Assign(IPVersionString, SymbolicValue()),

     CreateTag("L3", 0),

     Allocate(Tag("L3") + TTLOffset, 8),
     Assign(Tag("L3") +TTLOffset, SymbolicValue()),

     Allocate(Tag("L3") + IPLengthOffset, 16),
     Assign(Tag("L3") + IPLengthOffset, SymbolicValue()),

     Allocate(Tag("L3") + IPHeaderLengthOffset, 4),
     Assign(Tag("L3") + IPHeaderLengthOffset, SymbolicValue()),

     Allocate(Tag("L3") + IPIDOffset, 16),
     Assign(Tag("L3") +IPIDOffset, SymbolicValue())
   )(bigBang, true)

   afterBigBang._1.head
 }
}
