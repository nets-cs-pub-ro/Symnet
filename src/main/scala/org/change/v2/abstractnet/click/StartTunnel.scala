package org.change.v2.abstractnet.click

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}

/**
 * Element corresponding to: "[name] :: StartTunnel(sourceIp, destIP, id)"
 */
class StartTunnel(name: String,
                 inputPorts: List[Port],
                 outputPorts: List[Port],
                 configParams: List[ConfigParameter])
  extends GenericElement(name,
    "StartTunnel",
    inputPorts,
    outputPorts,
    configParams) {
  
  override def inputPortName(which: Int): String = s"${configParams(2).value}-start-in"
  override def outputPortName(which: Int): String = s"${configParams(2).value}-start-out"
    
}

class StartTunnelElementBuilder(name: String)
  extends ElementBuilder(name, "StartTunnel") {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new StartTunnel(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object StartTunnel {

  private var unnamedCount = 0

  private val genericElementName = "startTunnel"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): StartTunnelElementBuilder = {
    increment ; new StartTunnelElementBuilder(name)
  }

  def getBuilder: StartTunnelElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}


