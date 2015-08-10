package org.change.v2.abstractnet.click

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}

/**
 * Element corresponding to: "[name] :: Client(tcp, ip, destTcp, destIP)"
 */
class Client(name: String,
                 inputPorts: List[Port],
                 outputPorts: List[Port],
                 configParams: List[ConfigParameter])
  extends GenericElement(name,
    "Client",
    inputPorts,
    outputPorts,
    configParams) {

}

class ClientElementBuilder(name: String)
  extends ElementBuilder(name, "Client") {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new Client(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object Client {

  private var unnamedCount = 0

  private val genericElementName = "client"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): ClientElementBuilder = {
    increment ; new ClientElementBuilder(name)
  }

  def getBuilder: ClientElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}


