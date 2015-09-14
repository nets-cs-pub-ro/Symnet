package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{Instruction, LocationId}
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.memory.TagExp._

class IPsecESPEncap(name: String,
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
      //save fields first
      Allocate("ipid"),
      Allocate("ttl"),
      Allocate("proto"),
      Allocate("hchk"),
      Allocate("length"),
      Allocate("src"),
      Allocate("dst"),
      Assign("ipid",:@(IPID)),
      Assign("ttl",:@(TTL)),
      Assign("proto",:@(Proto)),
      Assign("hchk",:@(HeaderChecksum)),
      Assign("length",:@(IPLength)),
      Assign("src",:@(IPSrc)),
      Assign("dst",:@(IPDst)),


      //deallocate IP header
      Deallocate(IPVersion,4),
      Deallocate(IPHeaderLength,4),
      Deallocate(IPLength,16),
      Deallocate(IPID,16),
      Deallocate(TTL,8),
      Deallocate(Proto,8),
      Deallocate(HeaderChecksum,16),
      Deallocate(IPSrc,32),
      Deallocate(IPDst,32),

      //make space for ESP header
      CreateTag("L3",L3Tag-64),
      CreateTag("L4",L3Tag+160),

      //allocate IP Header
      Allocate(IPVersion,4),
      Assign(IPVersion,ConstantValue(4)),
      Allocate(IPHeaderLength,4),
      Assign(IPHeaderLength,ConstantValue(20)),
      Allocate(IPLength,16),
      Assign(IPLength,:+:(:@("length"),ConstantValue(18))),
      Allocate(IPID,16),
      Assign(IPID,:@("ipid")),
      Allocate(TTL,8),
      Assign(TTL,:@("ttl")),
      Allocate(Proto,8),
      Assign (Proto, ConstantValue(ESPProto)),
      Allocate(HeaderChecksum,16),
      Assign(HeaderChecksum,:@("hchk")),
      Allocate(IPSrc,32),
      Assign(IPSrc,:@("src")),
      Allocate(IPDst,32),
      Assign(IPDst,:@("dst")),

      //allocate ESP Header and footer!
      Allocate(ESPSPI,32),
      Assign(ESPSPI, ConstantValue(configParams(0).value.toInt)),
      Allocate(ESPSEQ,32),
      Assign(ESPSEQ, SymbolicValue()),
      CreateTag("END",EndTag+16),
      Allocate(ESPPadLength,8),
      Assign(ESPPadLength,ConstantValue(0)),
      Allocate(ESPNextProto,8),
      Assign(ESPNextProto,:@("proto")),

      //kill temp vars
      Deallocate("ipid"),
      Deallocate("ttl"),
      Deallocate("proto"),
      Deallocate("hchk"),
      Deallocate("length"),
      Deallocate("src"),
      Deallocate("dst"),

      Forward(outputPortName(0))
    )
  )
}

class IPsecESPEncapElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new IPsecESPEncap(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPsecESPEncap {
  private var unnamedCount = 0

  private val genericElementName = "ipsecespencap"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPsecESPEncapElementBuilder = {
    increment ; new IPsecESPEncapElementBuilder(name, "IPsecESPEncap")
  }

  def getBuilder: IPsecESPEncapElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
