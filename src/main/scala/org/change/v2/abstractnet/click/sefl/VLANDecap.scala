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

class VLANDecap(name: String,
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
      Constrain(Tag("L2")+EtherTypeOffset,:==:(ConstantValue(EtherProtoVLAN))),
      Allocate("s"),
      Assign("s",:@(Tag("L2")+EtherSrcOffset)),
      Allocate("d"),
      Assign("d",:@(Tag("L2")+EtherDstOffset)),
      Deallocate(Tag("L2")+EtherSrcOffset, 48),
      Deallocate(Tag("L2")+EtherDstOffset, 48),
      Allocate(Tag("L2")+EtherDstOffset, 48),
      CreateTag("L2",Tag("L2")+16),
      Allocate(Tag("L2")+EtherSrcOffset,48),
      Assign(Tag("L2")+EtherSrcOffset,:@("s")),
      Allocate(Tag("L2")+EtherDstOffset,48),
      Assign(Tag("L2")+EtherDstOffset,:@("d")),
      Deallocate("s"),
      Deallocate("d"),
      Forward(outputPortName(0))
    )
  )
}

class VLANDecapElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new VLANDecap(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object VLANDecap {
  private var unnamedCount = 0

  private val genericElementName = "vlandecap"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): VLANDecapElementBuilder = {
    increment ; new VLANDecapElementBuilder(name, "VLANDecap")
  }

  def getBuilder: VLANDecapElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
