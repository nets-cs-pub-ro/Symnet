package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.memory.Tag
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.util.canonicalnames._

/**
  * A small gift from radu to symnetic.
  */
class MPLSEncap(name: String,
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
      CreateTag("MPLS",Tag("L2")-32),
      Allocate(Label,20),
      Assign(Label, ConstantValue(configParams(0).value.toInt)),
      Allocate(TC,3),
      Assign(TC, ConstantValue(0)),
      Allocate(S,1),
      Assign(S, ConstantValue(1)),
      Allocate(MPLSTTL,8),
      Assign(MPLSTTL, ConstantValue(10)),
      Forward(outputPortName(0))
    )
  )
}

class MPLSEncapElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new MPLSEncap(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object MPLSEncap {
  private var unnamedCount = 0

  private val genericElementName = "mplsencap"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): MPLSEncapElementBuilder = {
    increment ; new MPLSEncapElementBuilder(name, "MPLSEncap")
  }

  def getBuilder: MPLSEncapElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}