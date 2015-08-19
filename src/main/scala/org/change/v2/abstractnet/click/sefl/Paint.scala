package org.change.v2.abstractnet.click.sefl

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 *
 * http://www.read.cs.ucla.edu/click/elements/paint
 */
import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.processingmodels.instructions.{Assign, Forward}
import org.change.v2.analysis.processingmodels.{InstructionBlock, LocationId, Instruction}

class Paint(name: String,
                   inputPorts: List[Port],
                   outputPorts: List[Port],
                   configParams: List[ConfigParameter])
  extends GenericElement(name,
    "Paint",
    inputPorts,
    outputPorts,
    configParams) {
  override def instructions: Map[LocationId, Instruction] = Map(
    (inputPortName(0), itsCode)
  )

  private def itsCode: Instruction = if (configParams.length == 2)
    // Then the optional ANNO param is present, then we write it too
    InstructionBlock(
      Assign("COLOR", ConstantValue(configParams(0).value.toInt)),
      Assign("ANNO", ConstantValue(configParams(1).value.toInt)),
      Forward(outputPortName(0))
    ) else InstructionBlock(
      Assign("COLOR", ConstantValue(configParams(0).value.toInt)),
      Forward(outputPortName(0))
    )
}

class PaintBuilder(name: String)
  extends ElementBuilder(name, "Paint") {

  addInputPort(Port())
  addOutputPort(Port())

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
