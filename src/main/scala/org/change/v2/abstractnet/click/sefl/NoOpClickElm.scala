package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.processingmodels.instructions.Forward
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}

class NoOpClickElm(name: String,
         elementType: String,
         inputPorts: List[Port],
         outputPorts: List[Port],
         configParams: List[ConfigParameter])
  extends GenericElement(name,
    elementType,
    inputPorts,
    outputPorts,
    configParams) {
  override def instructions: Map[LocationId, Instruction] = (for (p <- 0 until inputPorts.length) yield {
    (inputPortName(p) -> Forward(outputPortName(p)))
  }).toMap
}

class NoOpDeviceElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new NoOpClickElm(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object NoOpClickElm {
  private var unnamedCount = 0

  private val genericElementName = "noop"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String, elementType: String): NoOpDeviceElementBuilder = {
    increment ; new NoOpDeviceElementBuilder(name, elementType)
  }

  def getBuilder(elementType: String): NoOpDeviceElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount", elementType)
}