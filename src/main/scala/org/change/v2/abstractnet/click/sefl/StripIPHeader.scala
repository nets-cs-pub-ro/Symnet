package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.canonicalnames._

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

      Deallocate(Tag("L3")+IPVersion),
      Deallocate(Tag("L3")+IPHeaderLength),
      Deallocate(Tag("L3")+IPLength,16),
      Deallocate(Tag("L3")+IPID),
      Deallocate(Tag("L3")+TTL),
      Deallocate(Tag("L3")+Proto),
      Deallocate(Tag("L3")+HeaderChecksum),
      Deallocate(Tag("L3")+IPSrc),
      Deallocate(Tag("L3")+IPDst),

      if (Constrain(:@("t"),:==:(ConstantValue(ProtoIPIP))),
        InstructionBlock(
          Assign("L3",:+:(:@("L3"),20)),
          Assign("L4",:+:(:@("L3"),:@(Tag("L3")+IPHeaderLength)))
        ),
        Deallocate("L3")
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
