package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.memory.TagExp._
import org.change.v2.analysis.memory.Tag

class EtherDecap(name: String,
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
      Constrain(Tag("L2")+EtherTypeOffset,:==:(ConstantValue(EtherProtoIP))),
      //set eth addr anno
      Allocate("EtherSrc"),
      Assign("EtherSrc",:@(EtherSrc)),
      CreateTag("L3",Tag("L2") + 112),
      Deallocate(Tag("L2")+EtherSrcOffset, 48),
      Deallocate(Tag("L2")+EtherDstOffset, 48),
      Deallocate(Tag("L2")+EtherTypeOffset,16),
      DestroyTag("L2"),
      Forward(outputPortName(0))
    )
  )
}

class EtherDecapElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new EtherDecap(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object EtherDecap {
  private var unnamedCount = 0

  private val genericElementName = "etherDecap"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): EtherDecapElementBuilder = {
    increment ; new EtherDecapElementBuilder(name, "EtherDecap")
  }

  def getBuilder: EtherDecapElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
