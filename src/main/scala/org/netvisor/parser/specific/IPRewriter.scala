package org.netvisor.parser.specific

import parser.generic.{GenericElement, ConfigParameter, Port, ElementBuilder}
/**
 * radu
 * 3/5/14
 */

class IPRewriter(name: String,
         inputPorts: List[Port],
         outputPorts: List[Port],
         configParams: List[ConfigParameter])
  extends GenericElement(name,
    "IPRewriter",
    inputPorts,
    outputPorts,
    configParams) {

  lazy val configTokens = configParams(0).value.split("( )+")

  override def asHaskell(ruleNumber: Int) = ("r"+ruleNumber+" = ipRewriter \""+name+ "\" \"" + configTokens(1) + "\" \"" + configTokens(2)
    + "\" \"" + configTokens(3)+ "\" \"" + configTokens(4)+ "\"\n" +
    s"l$ruleNumber = r$ruleNumber : l${ruleNumber - 1}", 1)

}

class IPRewriterElementBuilder(name: String)
  extends ElementBuilder(name, "IPRewriter") {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new IPRewriter(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPRewriter {
  private var unnamedCount = 0

  private val genericElementName = "ipRewriter"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPRewriterElementBuilder = {
    increment ; new IPRewriterElementBuilder(name)
  }

  def getBuilder: IPRewriterElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
