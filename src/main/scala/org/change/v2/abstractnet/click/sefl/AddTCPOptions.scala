package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.memory.TagExp._
import org.change.v2.analysis.memory.Tag

class AddTCPOptions(name: String,
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
      //check that TCP header is allocated, modify TCP options field
      Allocate("OPT8"),
      Allocate("VAL8"),
      Assign("OPT8",ConstantValue(1)),
      Assign("VAL8",SymbolicValue()),
      Allocate("OPT9"),
      Allocate("VAL9"),
      Assign("OPT9",ConstantValue(0)),
      Assign("VAL9",SymbolicValue()),
      Allocate("OPT30"),
      Allocate("VAL30"),
      Assign("OPT30",SymbolicValue()),
      Assign("VAL30",SymbolicValue()),
      Allocate("OPT31"),
      Allocate("VAL31"),
      Assign("OPT31",SymbolicValue()),
      Assign("VAL31",SymbolicValue()),
      Forward(outputPortName(0))
    )
  )
}

class AddTCPOptionsElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new AddTCPOptions(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object AddTCPOptions {
  private var unnamedCount = 0

  private val genericElementName = "AddTCPOptions"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): AddTCPOptionsElementBuilder = {
    increment ; new AddTCPOptionsElementBuilder(name, "AddTCPOptions")
  }

  def getBuilder: AddTCPOptionsElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
