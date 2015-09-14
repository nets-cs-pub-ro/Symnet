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

class ScanTCPOptions(name: String,
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
//      Allocate("t"),
//      Assign("t",:@("OPT8")),
//
//      for (x <- "OPT.*")(
//        Assign(":x",ConstantValue(0))
//      ),
//
//      Assign("OPT8",:@("t")),
//      Deallocate("t"),
//      Forward(outputPortName(0))
    )
  )
}

class ScanTCPOptionsElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new ScanTCPOptions(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object ScanTCPOptions {
  private var unnamedCount = 0

  private val genericElementName = "ScanTCPOptions"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): ScanTCPOptionsElementBuilder = {
    increment ; new ScanTCPOptionsElementBuilder(name, "ScanTCPOptions")
  }

  def getBuilder: ScanTCPOptionsElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
