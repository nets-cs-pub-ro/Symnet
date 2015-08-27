package org.change.v2.abstractnet.click

/**
 * radu
 * 3/6/14
 */

import org.change.symbolicexec.blocks.click.IPFilterBlock
import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}

class IPFilter(name: String,
                 inputPorts: List[Port],
                 outputPorts: List[Port],
                 configParams: List[ConfigParameter])
  extends GenericElement(name,
    "IPFilter",
    inputPorts,
    outputPorts,
    configParams) {

  lazy val haskellParam = " [\"" + configParams.map(_.value).mkString("\", \"") + "\"]\n"

  override def outputPortName(which: Int): String = s"$name-out-$which"
}

class IPFilterElementBuilder(name: String)
  extends ElementBuilder(name, "IPFilter") {

  override def buildElement: IPFilter = {
//    Add ports for every pattern
    if (getInputPorts.isEmpty) for (_ <- getConfigParameters) {
      addInputPort(Port())
      addOutputPort(Port())
    }

    new IPFilter(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPFilter {
  private var unnamedCount = 0

  private val genericElementName = "ipFilter"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPFilterElementBuilder = {
    increment ; new IPFilterElementBuilder(name)
  }

  def getBuilder: IPFilterElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")

  def quickBuild(name: String, config: String) = IPFilter.getBuilder(name).handleConfigParameter(config).buildElement
}

