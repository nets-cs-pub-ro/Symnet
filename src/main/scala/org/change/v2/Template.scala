package org.change.v2

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.conversion.RepresentationConversion._

class Template(name: String,
                   elementType: String,
                   inputPorts: List[Port],
                   outputPorts: List[Port],
                   configParams: List[ConfigParameter])
  extends GenericElement(name,
    elementType,
    inputPorts,
    outputPorts,
    configParams) {

  override def instructions: Map[LocationId, Instruction] = Map(
    inputPortName(0) -> InstructionBlock(
      Assign("IPAddr22", ConstantValue( ipToNumber( configParams(0).value ) )),
      Assign("Ceva", ConstantValue( ipToNumber( configParams(1).value))),
      If (Constrain("IPAddr22", :<:(ConstantValue( ipToNumber( configParams(1).value)))),
        Forward(outputPortName(0)),
        Forward(outputPortName(1)))
    )
  )

  override def outputPortName(which: Int = 0): String = s"$name-$which-out"
}

class TemplateElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new Template(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object Template {
  private var unnamedCount = 0

  private val genericElementName = "template"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): TemplateElementBuilder = {
    increment ; new TemplateElementBuilder(name, "Template")
  }

  def getBuilder: TemplateElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
