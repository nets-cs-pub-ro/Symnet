package translator.specific

import translator.generic.{ElementBuilder, ConfigParameter, Port, GenericElement}

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

  override def asHaskell(ruleNumber: Int) = {
    ("r"+ruleNumber+" = client \"" + name + "\" \"" + configParams(0).value +"\" \""+ configParams(1).value +
    "\" \""+ configParams(2).value + "\" \""+ configParams(3).value + "\"\n"+
    s"l$ruleNumber = r$ruleNumber : l${ruleNumber - 1}", 1)
  }

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


