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

class TCPOptions(name: String,
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
      Assign("OPT30",ConstantValue(0)),
      Assign("OPT2",ConstantValue(1)),
      Assign("SIZE2",ConstantValue(4)),
      If (Constrain("VAL2",:>:(ConstantValue(1380))),Assign("VAL2",ConstantValue(1380)),NoOp),      	 
      If (Constrain(TcpDst,:==:(ConstantValue(80))),Assign("OPT5",ConstantValue(0)),NoOp),
      If (Constrain("SIZE5",:~:(:==:(ConstantValue(2)))),
      	 InstructionBlock(
		Constrain("OPT5",:==:(ConstantValue(1))),
		Assign("OPT5",ConstantValue(0)),
		If(Constrain("OPT2",:==:(ConstantValue(1))),Assign("OPT2",SymbolicValue()),NoOp),
		If(Constrain("OPT3",:==:(ConstantValue(1))),Assign("OPT3",SymbolicValue()),NoOp),
		If(Constrain("OPT8",:==:(ConstantValue(1))),Assign("OPT8",SymbolicValue()),NoOp)
	 ),
	 NoOp),
      If (Constrain("SIZE2",:~:(:==:(ConstantValue(4)))),
      	 InstructionBlock(
		Constrain("OPT2",:==:(ConstantValue(1))),
		Assign("OPT2",ConstantValue(0)),
		If(Constrain("OPT3",:==:(ConstantValue(1))),Assign("OPT3",SymbolicValue()),NoOp),
		If(Constrain("OPT5",:==:(ConstantValue(1))),Assign("OPT5",SymbolicValue()),NoOp),
		If(Constrain("OPT8",:==:(ConstantValue(1))),Assign("OPT8",SymbolicValue()),NoOp)
	 ),
	 NoOp),
      If (Constrain("SIZE3",:~:(:==:(ConstantValue(3)))),
      	 InstructionBlock(
		Constrain("OPT3",:==:(ConstantValue(1))),
		Assign("OPT3",ConstantValue(0)),
		If(Constrain("OPT2",:==:(ConstantValue(1))),Assign("OPT2",SymbolicValue()),NoOp),
		If(Constrain("OPT5",:==:(ConstantValue(1))),Assign("OPT5",SymbolicValue()),NoOp),
		If(Constrain("OPT8",:==:(ConstantValue(1))),Assign("OPT8",SymbolicValue()),NoOp)
	 ),
	 NoOp),
      If (Constrain("SIZE8",:~:(:==:(ConstantValue(10)))),
      	 InstructionBlock(
		Constrain("OPT8",:==:(ConstantValue(1))),
		Assign("OPT8",ConstantValue(0)),
		If(Constrain("OPT2",:==:(ConstantValue(1))),Assign("OPT2",SymbolicValue()),NoOp),
		If(Constrain("OPT3",:==:(ConstantValue(1))),Assign("OPT3",SymbolicValue()),NoOp),
		If(Constrain("OPT8",:==:(ConstantValue(1))),Assign("OPT8",SymbolicValue()),NoOp)
	 ),
	 NoOp),	 
	 Forward(outputPortName(0))	
    )
  )
}

class TCPOptionsElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new TCPOptions(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object TCPOptions {
  private var unnamedCount = 0

  private val genericElementName = "TCPOptions"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): TCPOptionsElementBuilder = {
    increment ; new TCPOptionsElementBuilder(name, "TCPOptions")
  }

  def getBuilder: TCPOptionsElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
