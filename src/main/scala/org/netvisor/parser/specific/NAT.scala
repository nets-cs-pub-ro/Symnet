package parser.specific

import parser.generic.{ElementBuilder, ConfigParameter, Port, GenericElement}

/**
 * Element corresponding to: "[name] :: NAT(outgoing ip address)"
 */
class NAT(name: String,
                 inputPorts: List[Port],
                 outputPorts: List[Port],
                 configParams: List[ConfigParameter])
  extends GenericElement(name,
    "NAT",
    inputPorts,
    outputPorts,
    configParams) {

  override def inputPortName(which: Int): String = which match {
    case 0 => s"$name-fw-in"
    case 1 => s"$name-bk-in"
  }

  override def outputPortName(which: Int): String = which match {
    case 0 => s"$name-fw-out"
    case 1 => s"$name-bk-out"
  }

  override def asHaskell(ruleNumber: Int) = {
    ("( r"+ruleNumber+", r" + s"${ruleNumber + 1}" + " ) = nAT \"" + name + "\" \"" + configParams(0).value + "\"\n"+
    s"l${ruleNumber+1} = r$ruleNumber : r${ruleNumber+1} : l${ruleNumber - 1}", 2)
  }

}

class NATElementBuilder(name: String)
  extends ElementBuilder(name, "NAT") {

  addInputPort(Port())
  addOutputPort(Port())
  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new NAT(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object NAT {

  private var unnamedCount = 0

  private val genericElementName = "NAT"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): NATElementBuilder = {
    increment ; new NATElementBuilder(name)
  }

  def getBuilder: NATElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}


