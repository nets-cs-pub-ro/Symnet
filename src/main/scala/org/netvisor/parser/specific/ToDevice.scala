package parser.specific

import parser.generic.{ElementBuilder, ConfigParameter, Port, GenericElement}

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

  override def asHaskell(ruleNumber: Int) = ("r"+ruleNumber+" = toDevice \"" + name + "\"\n" + s"l$ruleNumber = r$ruleNumber : l${ruleNumber - 1}", 1)

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