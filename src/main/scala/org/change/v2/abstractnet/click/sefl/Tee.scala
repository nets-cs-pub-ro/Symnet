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

class Tee(name: String,
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
      Allocate("tmp"),
      Assign("tmp",SymbolicValue()),

      If (Constrain("tmp",:>:(ConstantValue(100))),
        InstructionBlock(
          Deallocate("tmp"),
          Forward(outputPortName(0))
        ),
        InstructionBlock(
          Deallocate("tmp"),
          Forward(outputPortName(1))
        )
      )
    )
  )

  override def outputPortName(which: Int): String = s"$getName-out-$which"
}

class TeeElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  override def buildElement: GenericElement = {
    new Tee(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object Tee {
  private var unnamedCount = 0

  private val genericElementName = "Tee"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): TeeElementBuilder = {
    increment ; new TeeElementBuilder(name, "Tee")
  }

  def getBuilder: TeeElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
