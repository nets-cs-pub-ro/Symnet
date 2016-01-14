package org.change.v2.analysis.memory

import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.memory.TagExp._
import org.change.v2.analysis.processingmodels._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.util.canonicalnames._

import spray.json._

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
  def addInstructionToHistory(i: Instruction) = State(memory, history, errorCause, i :: instructionHistory)

  def jsonString = {
    import org.change.v2.analysis.memory.jsonformatters.StateToJson._
    this.toJson.toString
  }
  override def toString = jsonString
}

object State {

 def clean = State(MemorySpace.clean)

 def bigBang: State = {
   val bigBang = State(MemorySpace.clean)

   val afterBigBang = InstructionBlock (
     CreateTag("START",0),
     CreateTag("L3", 0),

     Allocate(IPVersion, 4),
     Assign(IPVersion, SymbolicValue()),

     Allocate(Proto, 8),
     Assign(Proto, SymbolicValue()),

     Allocate(IPSrc, 32),
     Assign(IPSrc, ConstantValue(org.change.utils.RepresentationConversion.ipToNumber("1.1.1.1"))),
     //Constrain(IPSrc, :&:(:>=:(ConstantValue(0)), :<=:(ConstantValue(4294967296L)))),
     Allocate(IPDst, 32),
     Assign(IPDst, SymbolicValue()),
     //Constrain(IPDst, :&:(:>=:(ConstantValue(0)), :<=:(ConstantValue(4294967296L)))),

     Allocate(TTL, 8),
     Assign(TTL, ConstantValue(255)),

     Allocate(IPLength, 16),
     Assign(IPLength, SymbolicValue()),

     Allocate(IPHeaderLength, 4),
     Assign(IPHeaderLength, SymbolicValue()),

     Allocate(HeaderChecksum,16),
     Assign(HeaderChecksum, SymbolicValue()),

     Allocate(IPID, 16),
     Assign(IPID, SymbolicValue()),

     CreateTag("L4", L3Tag + 160),

     Allocate(TcpSrc, 16),
     Assign(TcpSrc, SymbolicValue()),
     Constrain(TcpSrc, :&:(:>=:(ConstantValue(1000)), :<=:(ConstantValue(65536)))),

     Allocate(TcpDst, 16),
     Assign(TcpDst, SymbolicValue()),
     Constrain(TcpDst, :&:(:>=:(ConstantValue(1000)), :<=:(ConstantValue(65536)))),

     Allocate(TcpSeq, 32),
     Assign(TcpSeq, SymbolicValue()),

     Allocate(TcpAck, 32),
     Assign(TcpAck, SymbolicValue()),

     Allocate(TcpDataOffset, 4),
     Assign(TcpDataOffset, ConstantValue(160)),

     Allocate(TcpReserved,3),
     Assign(TcpReserved,SymbolicValue()),

     Allocate(TcpFlagNS,1),
     Assign(TcpFlagNS,ConstantValue(0)),
     Allocate(TcpFlagCWR,1),
     Assign(TcpFlagCWR,ConstantValue(0)),
     Allocate(TcpFlagECE,1),
     Assign(TcpFlagECE,ConstantValue(0)),
     Allocate(TcpFlagURG,1),
     Assign(TcpFlagURG,ConstantValue(0)),
     Allocate(TcpFlagACK,1),
     Assign(TcpFlagACK,SymbolicValue()),
     Allocate(TcpFlagACK,1),
     Assign(TcpFlagACK,SymbolicValue()),
     Allocate(TcpFlagSYN,1),
     Assign(TcpFlagSYN,SymbolicValue()),
     Allocate(TcpFlagRST,1),
     Assign(TcpFlagRST,ConstantValue(0)),
     Allocate(TcpFlagPSH,1),
     Assign(TcpFlagPSH,ConstantValue(0)),

     //CreateTag("PAYLOAD", :+:(L4Tag,:@(TcpDataOffset)),
     //Allocate(Tag("PAYLOAD"),12000),
     //Assign(Tag("PAYLOAD"),SymbolicValue()),
     CreateTag("END", L4Tag + 12000)
   )(bigBang, true)

   afterBigBang._1.head
 }
}
