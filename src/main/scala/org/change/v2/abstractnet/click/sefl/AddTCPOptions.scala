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

      Allocate("OPT1"),
      Allocate("SIZE1"),
      Assign("OPT1",ConstantValue(1)),
      Assign("SIZE1",ConstantValue(1)),
      
      Allocate("OPT2"),
      Allocate("SIZE2"),      
      Allocate("VAL2"),
//      Assign("OPT2",ConstantValue(1)),      
      Assign("OPT2",SymbolicValue()),      
      Assign("SIZE2",ConstantValue(4)),
//      Assign("SIZE2",SymbolicValue()),
      Assign("VAL2",SymbolicValue()),
      
      Allocate("OPT3"),
      Allocate("SIZE3"),
      Allocate("VAL3"),
//      Assign("OPT3",ConstantValue(1)),
      Assign("OPT3",SymbolicValue()),      
      Assign("SIZE3",ConstantValue(3)),
//      Assign("SIZE3",SymbolicValue()),
      Assign("VAL3",ConstantValue(7)),
      
      Allocate("OPT5"),
      Allocate("SIZE5"),
//      Assign("OPT5",ConstantValue(1)),
      Assign("OPT5",SymbolicValue()),      
      Assign("SIZE5",ConstantValue(2)),      
//      Assign("SIZE5",SymbolicValue()),
      
      Allocate("OPT8"),
      Allocate("VAL8"),
      Allocate("SIZE8"),	
//      Assign("OPT8",ConstantValue(1)),
      Assign("OPT8",SymbolicValue()),      
      Assign("SIZE8",ConstantValue(10)),
//      Assign("SIZE8",SymbolicValue()),
      Assign("VAL8",SymbolicValue()),

      Allocate("OPT30"),
      Allocate("VAL30"),
      Allocate("SIZE30"),	
//      Assign("OPT30",ConstantValue(1)),
      Assign("OPT30",SymbolicValue()),      
      Assign("SIZE30",ConstantValue(20)),
      Assign("VAL30",SymbolicValue()),

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
