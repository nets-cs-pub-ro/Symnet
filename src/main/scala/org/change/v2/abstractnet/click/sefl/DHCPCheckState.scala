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

class DHCPCheckState(name: String,
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
      Constrain("DHCPIP",:==:(:@(IPSrc))),
      Constrain("DHCPEth",:==:(:@("EtherSrc"))),
      Forward(outputPortName(0))
    )
  )
}

class DHCPCheckStateElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new DHCPCheckState(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object DHCPCheckState {
  private var unnamedCount = 0

  private val genericElementName = "DHCPCheckState"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): DHCPCheckStateElementBuilder = {
    increment ; new DHCPCheckStateElementBuilder(name, "DHCPCheckState")
  }

  def getBuilder: DHCPCheckStateElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
