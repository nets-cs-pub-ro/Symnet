package org.change.v2.abstractnet.click.sefl


import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.memory.TagExp._
import org.change.v2.analysis.memory.Tag

class IPDecap(name: String,
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
      Constrain(Proto, :==:(ConstantValue(configParams(0).value.toInt))),
      Assign("t",:@(Proto)),
      Deallocate(Proto, 8),
      Deallocate(IPDst, 32),
      Deallocate(IPSrc, 32),
      Deallocate(IPVersion, 4),
      Assign("t1",:@(IPLength)),
      Constrain("t1",:==:(:+:(:@(IPLength+160),ConstantValue(20)))),
      Deallocate(IPHeaderLength, 4),
      Deallocate(TTL, 8),
      Deallocate(IPID, 16),
      Deallocate(HeaderChecksum, 16),
      Deallocate(IPLength, 16),
      If(Constrain("t",:==:(ConstantValue(94))),
        CreateTag("L3",L3Tag+160),
        DestroyTag("L3")
      ),
      Deallocate("t"),
      Deallocate("t1"),
      Forward(outputPortName(0))
    )
  )
}

class IPDecapElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new IPDecap(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPDecap {
  private var unnamedCount = 0

  private val genericElementName = "ipdecap"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPDecapElementBuilder = {
    increment ; new IPDecapElementBuilder(name, "IPDecap")
  }

  def getBuilder: IPDecapElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
