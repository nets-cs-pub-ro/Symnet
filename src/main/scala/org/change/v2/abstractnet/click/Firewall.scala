package org.change.v2.abstractnet.click

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}

/**
 * Element corresponding to: "[name] :: Firewall"
 */
class Firewall(name: String,
                 inputPorts: List[Port],
                 outputPorts: List[Port],
                 configParams: List[ConfigParameter])
  extends GenericElement(name,
    "Firewall",
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

class FirewallElementBuilder(name: String)
  extends ElementBuilder(name, "Firewall") {

  addInputPort(Port())
  addOutputPort(Port())
  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new Firewall(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object Firewall {

  private var unnamedCount = 0

  private val genericElementName = "firewall"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): FirewallElementBuilder = {
    increment ; new FirewallElementBuilder(name)
  }

  def getBuilder: FirewallElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}


