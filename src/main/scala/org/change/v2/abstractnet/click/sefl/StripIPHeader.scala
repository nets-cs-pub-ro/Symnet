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
      Assign("t",:@(Tag("L3")+Proto)),

      Deallocate(Tag("L3")+IPVersion, 4),
      Deallocate(Tag("L3")+IPHeaderLength, 4),
      Deallocate(Tag("L3")+IPLength,16),
      Deallocate(Tag("L3")+IPID, 16),
      Deallocate(Tag("L3")+TTL, 8),
      Deallocate(Tag("L3")+Proto, 8),
      Deallocate(Tag("L3")+HeaderChecksum, 16),
      Deallocate(Tag("L3")+IPSrc, 32),
      Deallocate(Tag("L3")+IPDst, 32),

      If (Constrain("t",:==:(ConstantValue(ProtoIPIP))),
        InstructionBlock(
          CreateTag("L3",Tag("L3") + 20),
          CreateTag("L4",Tag("L3")+IPHeaderLength)
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
