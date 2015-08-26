package org.change.v2.abstractnet.click

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}

/**
 * Element corresponding to: "[name] :: Server(tcp, ip)"
 */
class Server(name: String,
                 inputPorts: List[Port],
                 outputPorts: List[Port],
                 configParams: List[ConfigParameter])
  extends GenericElement(name,
    "Server",
    inputPorts,
    outputPorts,
    configParams) {

}

class ServerElementBuilder(name: String)
  extends ElementBuilder(name, "Server") {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new Server(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object Server {

  private var unnamedCount = 0

  private val genericElementName = "server"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): ServerElementBuilder = {
    increment ; new ServerElementBuilder(name)
  }

  def getBuilder: ServerElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}


