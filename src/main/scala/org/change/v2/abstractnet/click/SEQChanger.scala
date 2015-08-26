package org.change.v2.abstractnet.click

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}

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


