package parser.specific

import parser.generic.{ElementBuilder, ConfigParameter, Port, GenericElement}

/**
 * Element corresponding to: "[name] :: EndTunnel(sourceIp, destIP, id)"
 */
class EndTunnel(name: String,
                 inputPorts: List[Port],
                 outputPorts: List[Port],
                 configParams: List[ConfigParameter])
  extends GenericElement(name,
    "EndTunnel",
    inputPorts,
    outputPorts,
    configParams) {

  override def inputPortName(which: Int): String = s"${configParams(2).value}-end-in"
  override def outputPortName(which: Int): String = s"${configParams(2).value}-end-out"

  override def asHaskell(ruleNumber: Int) = {
    ("r"+ruleNumber+" = tunnelOutEnd \"" + configParams(2).value + "\" \"" + configParams(0).value +"\" \""+ configParams(1).value +
      "\"\n"+
    s"l$ruleNumber = r$ruleNumber : l${ruleNumber - 1}", 1)
  }

}

class EndTunnelElementBuilder(name: String)
  extends ElementBuilder(name, "EndTunnel") {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new EndTunnel(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object EndTunnel {

  private var unnamedCount = 0

  private val genericElementName = "endTunnel"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): EndTunnelElementBuilder = {
    increment ; new EndTunnelElementBuilder(name)
  }

  def getBuilder: EndTunnelElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}


