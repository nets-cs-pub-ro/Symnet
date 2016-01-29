package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.processingmodels.instructions.{Allocate, Assign, Deallocate, Forward, InstructionBlock}
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.canonicalnames._

class AllMirror(name: String,
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
      Assign("tmp",:@(EtherSrc)),
      Assign(EtherSrc,:@(EtherDst)),
      Assign(EtherDst,:@("tmp")),

      Assign("tmp",:@(IPSrc)),
      Assign(IPSrc,:@(IPDst)),
      Assign(IPDst,:@("tmp")),

      Assign("tmp",:@(TcpSrc)),
      Assign(TcpSrc,:@(TcpDst)),
      Assign(TcpDst,:@("tmp")),
      Deallocate("tmp"),
      Forward(outputPortName(0))
    )
  )
}

class AllMirrorElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new AllMirror(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object AllMirror {
  private var unnamedCount = 0

  private val genericElementName = "AllMirror"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): AllMirrorElementBuilder = {
    increment ; new AllMirrorElementBuilder(name, "AllMirror")
  }

  def getBuilder: AllMirrorElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}

