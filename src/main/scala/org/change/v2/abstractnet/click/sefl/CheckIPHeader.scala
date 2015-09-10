package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.memory.TagExp._

class CheckIPHeader(name: String,
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
      Constrain(IPVersionOffset,:==:(ConstantValue(4))),
      Constrain(IPLengthOffset,:>=:(ConstantValue(MinPacketSize))),
      Constrain(IPHeaderLengthOffset,:>=:(ConstantValue(MinPacketSize))),
      Constrain(IPLengthOffset,:>=:(:@(IPHeaderLengthOffset))),
      Forward(outputPortName(0))
    )
  )
}

class CheckIPHeaderElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new CheckIPHeader(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object CheckIPHeader {
  private var unnamedCount = 0

  private val genericElementName = "checkIPheader"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): CheckIPHeaderElementBuilder = {
    increment ; new CheckIPHeaderElementBuilder(name, "CheckIPHeader")
  }

  def getBuilder: CheckIPHeaderElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
