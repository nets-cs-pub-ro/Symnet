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

class EtherEncap(name: String,
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
      CreateTag("L2",Tag("L3")-112),
      Allocate(Tag("L2")+EtherSrc,48),
      Assign(Tag("L2")+EtherSrc,ConstantValue(configParams(1).value.toInt)),
      Allocate(Tag("L2")+EtherDst,48),
      Assign(Tag("L2")+EtherDst,ConstantValue(configParams(2).value.toInt)),
      Allocate(Tag("L2")+EtherType,16),
      Assign(Tag("L2")+EtherType,ConstantValue(configParams(0).value.toInt)),
      Forward(outputPortName(0))
    )
  )
}

class EtherEncapElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new EtherEncap(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object EtherEncap {
  private var unnamedCount = 0

  private val genericElementName = "etherencap"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): EtherEncapElementBuilder = {
    increment ; new EtherEncapElementBuilder(name, "EtherEncap")
  }

  def getBuilder: EtherEncapElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
