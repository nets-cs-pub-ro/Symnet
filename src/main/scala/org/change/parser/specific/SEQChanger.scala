package parser.specific

import parser.generic.{ElementBuilder, ConfigParameter, Port, GenericElement}

/**
 * Element corresponding to: "[name] :: SEQChanger"
 */
class SEQChanger(name: String,
                 inputPorts: List[Port],
                 outputPorts: List[Port],
                 configParams: List[ConfigParameter])
  extends GenericElement(name,
    "SEQChanger",
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
    ("( r"+ruleNumber+", r" + s"${ruleNumber + 1}" + " ) = sequenceMangler \"" + name + "\"\n"+
    s"l${ruleNumber+1} = r$ruleNumber : r${ruleNumber+1} : l${ruleNumber - 1}", 2)
  }

}

class SEQChangerElementBuilder(name: String)
  extends ElementBuilder(name, "SEQChanger") {

  addInputPort(Port())
  addOutputPort(Port())
  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new SEQChanger(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object SEQChanger {

  private var unnamedCount = 0

  private val genericElementName = "seqChanger"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): SEQChangerElementBuilder = {
    increment ; new SEQChangerElementBuilder(name)
  }

  def getBuilder: SEQChangerElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}


