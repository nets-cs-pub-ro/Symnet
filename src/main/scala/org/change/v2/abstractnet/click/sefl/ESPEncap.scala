package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.canonicalnames._

class IPEncap(name: String,
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
      Allocate(Proto),
      Assign (Proto, ConstantValue(configParams(0).value.toInt)),
      Allocate(IPSrc),
      Assign(IPSrc,ConstantValue(ipToNumber(configParams(1).value))),
      Allocate(IPDst),
      Assign(IPDst,ConstantValue(ipToNumber(configParams(2).value))),
      Allocate(IPVersion),
      Assign(IPVersion,ConstantValue(4)),
      Assign("t",:@(IPLength)),
      Allocate(IPLength),
      Assign(IPLength,:+:(:@("t"),ConstantValue(20))),
      Assign("t",:@(IPHeaderLength)),
      Allocate(IPHeaderLength),
      Assign(IPHeaderLength,:@("t")),
      Assign("t",:@(TTL)),
      Allocate(TTL),
      Assign(TTL,:@("t")),
      Allocate(IPID),
      Assign(IPID,SymbolicValue()),
      Forward(outputPortName(0))
    )
  )
}

class IPEncapElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new IPEncap(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPEncap {
  private var unnamedCount = 0

  private val genericElementName = "ipencap"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPEncapElementBuilder = {
    increment ; new IPEncapElementBuilder(name, "IPEncap")
  }

  def getBuilder: IPEncapElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
