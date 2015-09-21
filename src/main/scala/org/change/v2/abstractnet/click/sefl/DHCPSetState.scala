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

class DHCPSetState(name: String,
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
      Allocate("DHCPIP"),
      Assign("DHCPIP", :@(IPSrc)),
      Allocate("DHCPETH"),
      Assign("DHCPETH", :@(EtherSrc)),
      Forward(outputPortName(0))
    )
  )
}

class DHCPSetStateElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new DHCPSetState(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object DHCPSetState {
  private var unnamedCount = 0

  private val genericElementName = "DHCPSetState"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): DHCPSetStateElementBuilder = {
    increment ; new DHCPSetStateElementBuilder(name, "DHCPSetState")
  }

  def getBuilder: DHCPSetStateElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
