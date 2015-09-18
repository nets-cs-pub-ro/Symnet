package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.memory.TagExp._
import org.change.v2.analysis.memory.Tag

class ICMPPingSource(name: String,
                   elementType: String,
                   inputPorts: List[Port],
                   outputPorts: List[Port],
                   configParams: List[ConfigParameter])
  extends GenericElement(name,

    elementType,
    inputPorts,
    outputPorts,
    configParams) {

  override def instructions: Map[LocationId, Instruction] = Map(
    inputPortName(0) -> InstructionBlock(
      //create IPEncap
      Allocate(IPVersion,4),
      Assign(IPVersion,ConstantValue(4)),

      Allocate(IPHeaderLength,4),
      Assign(IPHeaderLength,ConstantValue(20)),
      Allocate(IPLength,16),
      Assign(IPLength,ConstantValue(20)),
      Allocate(IPID,16),
      Assign(IPID,SymbolicValue()),
      Allocate(TTL,8),
      Assign(TTL,ConstantValue(255)),
      Allocate(Proto,8),
      Assign (Proto, ConstantValue(ICMPProto)),
      Allocate(HeaderChecksum,16),
      Assign(HeaderChecksum,SymbolicValue()),
      Allocate(IPSrc,32),
      Assign(IPSrc,ConstantValue(ipToNumber(configParams(0).value))),
      Allocate(IPDst,32),
      Assign(IPDst,ConstantValue(ipToNumber(configParams(1).value))),

      CreateTag("L4", L3Tag + 160), 
      Allocate(ICMPType,8),
      Assign(ICMPType,ConstantValue(8)),
      Allocate(ICMPCode,8),
      Assign(ICMPCode,ConstantValue(0)),
      Allocate(ICMPHeaderChecksum,16),
      Assign(ICMPHeaderChecksum,SymbolicValue()),
      Allocate(ICMPIdentifier,16),
      Assign(ICMPIdentifier, SymbolicValue()),
      Allocate(ICMPSEQ,16),
      Assign(ICMPSEQ, SymbolicValue()),
      Allocate(ICMPData,configParams(2).value.toInt),
      Assign(ICMPData, SymbolicValue()),
      Forward(outputPortName(0))
    )
  )
}

class ICMPPingSourceElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new ICMPPingSource(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object ICMPPingSource {
  private var unnamedCount = 0

  private val genericElementName = "ICMPPingSource"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): ICMPPingSourceElementBuilder = {
    increment ; new ICMPPingSourceElementBuilder(name, "ICMPPingSource")
  }

  def getBuilder: ICMPPingSourceElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
