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

class IPMirror(name: String,
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
      //check that IP header is allocated, swap addresses
      Allocate("tmp"),
      Assign("tmp",:@(IPSrc)),
      Assign(IPSrc,:@(IPDst)),
      Assign(IPDst,:@("tmp")),
      Deallocate("tmp"),
      Forward(outputPortName(0))
    )
  )
}

class IPMirrorElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new IPMirror(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPMirror {
  private var unnamedCount = 0

  private val genericElementName = "IPMirror"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPMirrorElementBuilder = {
    increment ; new IPMirrorElementBuilder(name, "IPMirror")
  }

  def getBuilder: IPMirrorElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
