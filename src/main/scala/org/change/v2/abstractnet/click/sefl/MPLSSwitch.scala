package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.memory.Tag
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.util.canonicalnames.{Label, MPLSTTL, S, TC}

/**
  * A small gift from radu to symnetic.
  */
class MPLSSwitch(name: String,
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
    inputPortName(0) -> Fork(
      configParams.zipWithIndex.map(pi => InstructionBlock(
        Constrain(Label, :==:(ConstantValue(pi._1.value.toInt))),
        Forward(outputPortName(pi._2))
      ))
    )
  )

  override def outputPortName(which: Int): String = s"$getName-out-$which"
}

class MPLSSwitchElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  override def buildElement: GenericElement = {
    new MPLSSwitch(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object MPLSSwitch {
  private var unnamedCount = 0

  private val genericElementName = "mplsswitch"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): MPLSSwitchElementBuilder = {
    increment ; new MPLSSwitchElementBuilder(name, "MPLSSwitch")
  }

  def getBuilder: MPLSSwitchElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}