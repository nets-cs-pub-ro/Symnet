package org.change.v2.abstractnet.click

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}

class IPClassifier(name: String,
                    inputPorts: List[Port],
                    outputPorts: List[Port],
                    configParams: List[ConfigParameter])
  extends GenericElement(name,
    "IPClassifier",
    inputPorts,
    outputPorts,
    configParams) {

}

class IPClassifierElementBuilder(name: String)
  extends ElementBuilder(name, "IPClassifier") {

  addInputPort(Port())

  override def buildElement: GenericElement = {
    new IPClassifier(name, getInputPorts, getOutputPorts, getConfigParameters)
  }

  override def handleConfigParameter(paramString: String): ElementBuilder = {
    super.handleConfigParameter(paramString)
    addOutputPort(Port(Some(paramString)))
  }
}

object IPClassifier {

  private var unnamedCount = 0

  private val genericElementName = "IPClassifier"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPClassifierElementBuilder = {
    increment ; new IPClassifierElementBuilder(name)
  }

  def getBuilder: IPClassifierElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}