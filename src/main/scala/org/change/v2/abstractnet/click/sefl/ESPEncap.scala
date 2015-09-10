package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.memory.TagExp._

class ESPEncap(name: String,
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
      Allocate(ProtoOffset, 8),
      Assign (ProtoOffset, ConstantValue(configParams(0).value.toInt)),
      Allocate(IPSrcOffset, 32),
      Assign(IPSrcOffset,ConstantValue(ipToNumber(configParams(1).value))),
      Allocate(IPDstOffset, 32),
      Assign(IPDstOffset,ConstantValue(ipToNumber(configParams(2).value))),
      Allocate(IPVersionOffset, 4),
      Assign(IPVersionOffset,ConstantValue(4)),
      Assign("t",:@(IPLengthOffset)),
      Allocate(IPLengthOffset, 16),
      Assign(IPLengthOffset,:+:(:@("t"),ConstantValue(20))),
      Assign("t",:@(IPHeaderLengthOffset)),
      Allocate(IPHeaderLengthOffset, 4),
      Assign(IPHeaderLengthOffset,:@("t")),
      Assign("t",:@(TTLOffset)),
      Allocate(TTLOffset, 8),
      Assign(TTLOffset,:@("t")),
      Allocate(IPIDOffset, 16),
      Assign(IPIDOffset,SymbolicValue()),
      Forward(outputPortName(0))
    )
  )
}

class ESPEncapElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new ESPEncap(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object ESPEncap {
  private var unnamedCount = 0

  private val genericElementName = "ipencap"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): ESPEncapElementBuilder = {
    increment ; new ESPEncapElementBuilder(name, "IPEncap")
  }

  def getBuilder: ESPEncapElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
