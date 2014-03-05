package parser.specific

import parser.generic.{ElementBuilder, ConfigParameter, Port, GenericElement}

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

  override def asHaskell(ruleNumber: Int) = {
    ("r"+ruleNumber+" = server \"" + name + "\" \"" + configParams(0).value +"\" \""+ configParams(1).value + "\"\n" +
      s"l$ruleNumber = r$ruleNumber : l${ruleNumber - 1}", 1)
  }

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


