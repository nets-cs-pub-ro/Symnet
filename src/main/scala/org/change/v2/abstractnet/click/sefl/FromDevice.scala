package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.processingmodels._
import org.change.v2.analysis.processingmodels.instructions.Forward

/**
 * Element corresponding to: "[name] :: FromDevice(deviceName)"
 * @param name
 * @param deviceName
 */
class FromDevice(name: String,
                 inputPorts: List[Port],
                 outputPorts: List[Port],
                 configParams: List[ConfigParameter])
  extends GenericElement(name,
    "FromDevice",
    inputPorts,
    outputPorts,
    configParams) {

  override def instructions: Map[LocationId, Instruction] = Map(
    (inputPortName(0), Forward(outputPortName(0)))
  )
}

class FromDeviceElementBuilder(name: String)
  extends ElementBuilder(name, "FromDevice") {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new FromDevice(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object FromDevice {

  private var unnamedCount = 0

  private val genericElementName = "fromDevice"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): FromDeviceElementBuilder = {
    increment ; new FromDeviceElementBuilder(name)
  }

  def getBuilder: FromDeviceElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}


