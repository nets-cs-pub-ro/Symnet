package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ElementBuilder, GenericElement, ConfigParameter, Port}
import org.change.v2.analysis.constraint.EQ_E
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.processingmodels._
import org.change.v2.analysis.processingmodels.instructions._

/**
 * A small gift from radu to symnetic.
 */
class PaintSwitch(name: String,
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
      Seq(
      InstructionBlock(
        //          Constrain("COLOR", :==:(ConstantValue(color))),
        ConstrainNamedSymbol(Paint.COLOR, EQ_E(ConstantValue(1))),
        Forward(outputPortName(1))
      ),
      InstructionBlock(
        //          Constrain("COLOR", :==:(ConstantValue(color))),
        ConstrainNamedSymbol(Paint.COLOR, EQ_E(ConstantValue(2))),
        Forward(outputPortName(2))
      ))
    )
  )

  override def outputPortName(which: Int): String = s"$getName-out-$which"
}

class PaintSwitchElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  override def buildElement: GenericElement = {
    new PaintSwitch(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object PaintSwitch {
  private var unnamedCount = 0

  private val genericElementName = "PaintSwitch"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): PaintSwitchElementBuilder = {
    increment ; new PaintSwitchElementBuilder(name, "PaintSwitch")
  }

  def getBuilder: PaintSwitchElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}

