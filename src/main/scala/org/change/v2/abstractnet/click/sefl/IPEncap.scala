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

      Assign("t",:@(Tag("L3")+IPLength)),
      Assign("t2",:@(Tag("L3")+TTL)),

      CreateTag("L3",Tag("L3")+20),

      Allocate(Tag("L3")+IPVersion,4),
      Assign(Tag("L3")+IPVersion,ConstantValue(4)),

      Allocate(Tag("L3")+IPHeaderLength,4),
      Assign(Tag("L3")+IPHeaderLength,ConstantValue(20)),
      Allocate(Tag("L3")+IPLength,16),
      Assign(Tag("L3")+IPLength,:+:(:@("t"),ConstantValue(20))),      
      Allocate(Tag("L3")+IPID,16),
      Assign(IPID,SymbolicValue()),
      Allocate(Tag("L3")+TTL,8),
      Assign(Tag("L3")+TTL,:@("t2")),
      Allocate(Tag("L3")+Proto,8),
      Assign (Tag("L3")+Proto, ConstantValue(configParams(0).value.toInt)),
      Allocate(Tag("L3")+HeaderChecksum,16),
      Assign(Tag("L3")+HeaderChecksum,SymbolicValue()),
      Allocate(Tag("L3")+IPSrc,32),
      Assign(Tag("L3")+IPSrc,ConstantValue(ipToNumber(configParams(1).value))),
      Allocate(Tag("L3")+IPDst,32),
      Assign(Tag("L3")+IPDst,ConstantValue(ipToNumber(configParams(2).value))),
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
