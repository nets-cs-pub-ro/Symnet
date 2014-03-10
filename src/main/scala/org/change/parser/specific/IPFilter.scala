package org.change.parser.specific

/**
 * radu
 * 3/6/14
 */
import parser.generic.{GenericElement, ConfigParameter, Port, ElementBuilder}

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

  override def asHaskell(ruleNumber: Int) = ("r"+ruleNumber+" = ipFilter \""+name+ "\"" + haskellParam +
    s"l$ruleNumber = r$ruleNumber ++ l${ruleNumber - 1}", configParams.length)

  override def outputPortName(which: Int): String = s"$name-out-$which"
}

class IPFilterElementBuilder(name: String)
  extends ElementBuilder(name, "IPFilter") {

  /**
   * Add one port for each output device.
   */
  for (_ <- getConfigParameters) {
    addInputPort(Port())
    addOutputPort(Port())
  }

  override def buildElement: IPFilter = {
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

