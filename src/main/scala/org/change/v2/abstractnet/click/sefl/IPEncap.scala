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

class IPEncap(name: String,
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
      Allocate("t"),
      Allocate("t2"),

      Assign("t",:@(Tag("L3")+IPLengthOffset)),
      Assign("t2",:@(Tag("L3")+TTLOffset)),

      CreateTag("L3",Tag("L3")-160),

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
      Assign (Tag("L3")+ProtoOffset, ConstantValue(configParams(0).value.toInt)),
      Allocate(Tag("L3")+HeaderChecksumOffset,16),
      Assign(Tag("L3")+HeaderChecksumOffset,SymbolicValue()),
      Allocate(Tag("L3")+IPSrcOffset,32),
      Assign(Tag("L3")+IPSrcOffset,ConstantValue(ipToNumber(configParams(1).value))),
      Allocate(Tag("L3")+IPDstOffset,32),
      Assign(Tag("L3")+IPDstOffset,ConstantValue(ipToNumber(configParams(2).value))),
      Deallocate("t"),
      Deallocate("t2"),
      Forward(outputPortName(0))
    )
  )
}

class IPEncapElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new IPEncap(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPEncap {
  private var unnamedCount = 0

  private val genericElementName = "ipencap"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPEncapElementBuilder = {
    increment ; new IPEncapElementBuilder(name, "IPEncap")
  }

  def getBuilder: IPEncapElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
