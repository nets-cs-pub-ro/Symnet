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

class StripIPHeader(name: String,
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
      Assign("t",:@(Tag("L3")+ProtoOffset)),

      Deallocate(Tag("L3")+IPVersionOffset, 4),
      Deallocate(Tag("L3")+IPHeaderLengthOffset, 4),
      Deallocate(Tag("L3")+IPLengthOffset,16),
      Deallocate(Tag("L3")+IPIDOffset, 16),
      Deallocate(Tag("L3")+TTLOffset, 8),
      Deallocate(Tag("L3")+ProtoOffset, 8),
      Deallocate(Tag("L3")+HeaderChecksumOffset, 16),
      Deallocate(Tag("L3")+IPSrcOffset, 32),
      Deallocate(Tag("L3")+IPDstOffset, 32),

      If (Constrain("t",:==:(ConstantValue(IPIPProto))),
        InstructionBlock(
          CreateTag("L3",Tag("L3") + 160),
          CreateTag("L4",Tag("L3")+IPHeaderLengthOffset)
        ),
        Deallocate(Tag("L3"),0) // TODO: Should correct this
      ),
      Forward(outputPortName(0))
    )
  )
}

class StripIPHeaderElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new StripIPHeader(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object StripIPHeader {
  private var unnamedCount = 0

  private val genericElementName = "stripIPheader"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): StripIPHeaderElementBuilder = {
    increment ; new StripIPHeaderElementBuilder(name, "StripIPHeader")
  }

  def getBuilder: StripIPHeaderElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
