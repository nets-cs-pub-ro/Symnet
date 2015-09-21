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

      Allocate(IPVersion,4),
      Assign(IPVersion,ConstantValue(4)),

      Allocate(IPHeaderLength,4),
      Assign(IPHeaderLength,ConstantValue(20)),
      Allocate(IPLength,16),
      Assign(IPLength,:+:(:@("t"),ConstantValue(20))),
      Allocate(IPID,16),
      Assign(IPID,SymbolicValue()),
      Allocate(TTL,8),
      Assign(TTL,:@("t2")),
      Allocate(Proto,8),
      Assign (Proto, ConstantValue(configParams(0).value.toInt)),
      Allocate(HeaderChecksum,16),
      Assign(HeaderChecksum,SymbolicValue()),
      Allocate(IPSrc,32),
      Assign(IPSrc,ConstantValue(ipToNumber(configParams(1).value))),
      Allocate(IPDst,32),
      Assign(IPDst,ConstantValue(ipToNumber(configParams(2).value))),
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
