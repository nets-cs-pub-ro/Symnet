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
      CreateTag("L3",Tag("L3")),

      Allocate(Tag("L3")+IPVersionOffset,4),
      Assign(Tag("L3")+IPVersionOffset,ConstantValue(4)),

      Allocate(Tag("L3")+IPHeaderLengthOffset,4),
      Assign(Tag("L3")+IPHeaderLengthOffset,ConstantValue(20)),
      Allocate(Tag("L3")+IPLengthOffset,16),
      Assign(Tag("L3")+IPLengthOffset,:+:(:@("t"),ConstantValue(20))),
      Allocate(Tag("L3")+IPIDOffset,16),
      Assign(IPIDOffset,SymbolicValue()),
      Allocate(Tag("L3")+TTLOffset,8),
      Assign(Tag("L3")+TTLOffset,:@("t2")),
      Allocate(Tag("L3")+ProtoOffset,8),
      Assign (Tag("L3")+ProtoOffset, ConstantValue(ICMPProto)),
      Allocate(Tag("L3")+HeaderChecksumOffset,16),
      Assign(Tag("L3")+HeaderChecksumOffset,SymbolicValue()),
      Allocate(Tag("L3")+IPSrcOffset,32),
      Assign(Tag("L3")+IPSrcOffset,ConstantValue(ipToNumber(configParams(0).value))),
      Allocate(Tag("L3")+IPDstOffset,32),
      Assign(Tag("L3")+IPDstOffset,ConstantValue(ipToNumber(configParams(1).value))),

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
      Allocate(ICMPData,ConstantValue(configParams(2).value.toInt)),
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
