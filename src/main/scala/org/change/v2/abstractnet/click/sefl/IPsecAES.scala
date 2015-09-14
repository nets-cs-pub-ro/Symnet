package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.memory.TagExp._

class IPsecAES(name: String,
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
      //only works for ESP encapsulated packets. 
      Constrain(Proto, :==:(ConstantValue(ESPProto))),

      //set L4 tag to access TCP fields!
      CreateTag("L4",L4Tag+64),

      (if (configParams(0).value.toInt==0)
        InstructionBlock(
          //decrypt
          //remember payload!
          //encrypt TCP payload
          Deallocate(TcpSrc,16),
          Deallocate(TcpDst,16),
          Deallocate(TcpSeq,32),
          Deallocate(TcpAck,32),
          Constrain(configParams(1).value,:==:(:@(TcpSrc)))
        )
      else
        InstructionBlock(
          //encrypt
          //remember payload!
          Allocate(configParams(1).value),
          Assign(configParams(1).value,:@(TcpSrc)),

          //encrypt TCP payload
          Allocate(TcpSrc,16),
          Assign(TcpSrc,SymbolicValue()),
          Allocate(TcpDst,16),
          Assign(TcpDst,SymbolicValue()),
          Allocate(TcpSeq,32),
          Assign(TcpSeq,SymbolicValue()),
          Allocate(TcpAck,32),
          Assign(TcpAck,SymbolicValue())
        )
      ),
      //revert L4 tag
      CreateTag("L4",L4Tag-64),
      Forward(outputPortName(0))
    )
  )
}

class IPsecAESElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new IPsecAES(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPsecAES {
  private var unnamedCount = 0

  private val genericElementName = "ipsecaes"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPsecAESElementBuilder = {
    increment ; new IPsecAESElementBuilder(name, "IPsecAES")
  }

  def getBuilder: IPsecAESElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
