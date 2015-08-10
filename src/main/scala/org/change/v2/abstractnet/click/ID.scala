package org.change.v2.abstractnet.click

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}

class ID(name: String,
         elementType: String,
         inputPorts: List[Port],
         outputPorts: List[Port],
         configParams: List[ConfigParameter])
  extends GenericElement(name,
    elementType,
    inputPorts,
    outputPorts,
    configParams) {

}

class IDDeviceElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new ID(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object ID {
  private var unnamedCount = 0

  private val genericElementName = "id"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String, elementType: String): IDDeviceElementBuilder = {
    increment ; new IDDeviceElementBuilder(name, elementType)
  }

  def getBuilder(elementType: String): IDDeviceElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount", elementType)
}