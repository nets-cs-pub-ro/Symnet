package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.processingmodels._
import org.change.v2.analysis.processingmodels.instructions.Forward

/**
 * Element corresponding to: "[name] :: ToDevice(deviceName) or FromNetPort(mac)"
 * @param name
 * @param deviceName
 */
class ToDevice(name: String,
               inputPorts: List[Port],
               outputPorts: List[Port],
               configParams: List[ConfigParameter])
  extends GenericElement(name,
    "ToDevice",
    inputPorts,
    outputPorts,
    configParams) {

  override def instructions: Map[LocationId, Instruction] = Map(
    (inputPortName(0), Forward(outputPortName(0)))
  )

  }

class ToDeviceElementBuilder(name: String)
  extends ElementBuilder(name, "ToDevice") {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new ToDevice(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object ToDevice {

  private var unnamedCount = 0

  private val genericElementName = "toDevice"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): ToDeviceElementBuilder = {
    increment ; new ToDeviceElementBuilder(name)
  }

  def getBuilder: ToDeviceElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}