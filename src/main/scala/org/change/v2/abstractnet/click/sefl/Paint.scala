package org.change.v2.abstractnet.click.sefl

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.processingmodels.Instruction

class Paint(name: String,
                   inputPorts: List[Port],
                   outputPorts: List[Port],
                   configParams: List[ConfigParameter])
  extends GenericElement(name,
    "Paint",
    inputPorts,
    outputPorts,
    configParams) {
  override def instructions: Map[Int, (Instruction, String)] = Map(
//    ( (name, ))
  )
}

class PaintBuilder(name: String)
  extends ElementBuilder(name, "Paint") {

  addInputPort(Port())

  override def buildElement: GenericElement = {
    new Paint(name, getInputPorts, getOutputPorts, getConfigParameters)
  }

  override def handleConfigParameter(paramString: String): ElementBuilder = {
    super.handleConfigParameter(paramString)
    addOutputPort(Port(Some(paramString)))
  }
}

object Paint {

  private var unnamedCount = 0

  private val genericElementName = "Paint"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): PaintBuilder = {
    increment ; new PaintBuilder(name)
  }

  def getBuilder: PaintBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
