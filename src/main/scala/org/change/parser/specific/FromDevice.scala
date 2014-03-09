package parser.specific

import parser.generic.{ElementBuilder, ConfigParameter, Port, GenericElement}

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

  override def asHaskell(ruleNumber: Int) = ("r"+ruleNumber+" = fromDevice \"" + name + "\"\n" + s"l$ruleNumber = r$ruleNumber : l${ruleNumber - 1}", 1)

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


