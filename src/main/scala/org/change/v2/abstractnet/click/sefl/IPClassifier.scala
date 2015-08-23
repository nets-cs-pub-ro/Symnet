package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete._
import org.change.v2.analysis.processingmodels._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.util.canonicalnames._
import org.change.v2.util.conversion.NumberFor
import org.change.v2.util.conversion.RepresentationConversion._
import org.change.v2.util.regexes._

class IPClassifier(name: String,
                    inputPorts: List[Port],
                    outputPorts: List[Port],
                    configParams: List[ConfigParameter])
  extends GenericElement(name,
    "IPClassifier",
    inputPorts,
    outputPorts,
    configParams) {

  val lastIndex = configParams.length - 1

  val conditionSeparator = """\s+(and|&&)\s+"""

  val ipProto = ("ip proto (" + number + ")").r

  val srcHostAddr = ("src host (" + ipv4 + ")").r
  val srcNetAddr = ("src net (" + ipv4 + ")/(" + number + ")").r
  val srcNetExplicitAddr = ("src net (" + ipv4 + ") mask (" + ipv4 + ")").r

  val dstHostAddr = ("dst host (" + ipv4 + ")").r
  val dstNetAddr = ("dst net (" + ipv4 + ")/(" + number + ")").r
  val dstNetExplicitAddr = ("dst net (" + ipv4 + ") mask (" + ipv4 + ")").r

  val srcPort = ("src (tcp|udp) port (" + number + ")").r
  val dstPort = ("dst (tcp|udp) port (" + number + ")").r

  val tcp = "tcp".r
  val udp = "udp".r

  private def conditionToConstraint(condition: String): Constrain = condition match {
    case ipProto(v) => Constrain(IPVersion, :==:(ConstantValue(v.toInt)))

    case srcHostAddr(ip) => Constrain(IPSrc, :==:(ConstantValue(ipToNumber(ip))))
    case dstHostAddr(ip) => Constrain(IPDst, :==:(ConstantValue(ipToNumber(ip))))

    case dstNetAddr(ip, mask) => {
      val (lower, upper) = ipAndMaskToInterval(ip, mask)
      Constrain(IPDst, :&:(:>=:(ConstantValue(lower)), :<=:(ConstantValue(upper))))
    }
    case dstNetExplicitAddr(ip, mask) => {
      val (lower, upper) = ipAndExplicitMaskToInterval(ip, mask)
      Constrain(IPDst, :&:(:>=:(ConstantValue(lower)), :<=:(ConstantValue(upper))))
    }
    case srcNetAddr(ip, mask) => {
      val (lower, upper) = ipAndMaskToInterval(ip, mask)
      Constrain(IPSrc, :&:(:>=:(ConstantValue(lower)), :<=:(ConstantValue(upper))))
    }
    case srcNetExplicitAddr(ip, mask) => {
      val (lower, upper) = ipAndExplicitMaskToInterval(ip, mask)
      Constrain(IPSrc, :&:(:>=:(ConstantValue(lower)), :<=:(ConstantValue(upper))))
    }

    case srcPort(port) => Constrain(PortSrc, :==:(ConstantValue(port.toInt)))
    case dstPort(port) => Constrain(PortDst, :==:(ConstantValue(port.toInt)))

    case tcp() => Constrain(Proto, :==:(ConstantValue(NumberFor("tcp"))))
    case udp() => Constrain(Proto, :==:(ConstantValue(NumberFor("udp"))))

  }

  val any = """\s*(true|\-)\s*""".r
  val none = """\s*false\s*""".r

  val portToInstr = scala.collection.mutable.Map[Int, Instruction]()

  for {
    (p,i) <- configParams.zipWithIndex.reverse
  } {
    portToInstr += ((i, paramsToInstructionBlock(p.value,i)))
  }

  override def instructions: Map[LocationId, Instruction] = Map( inputPortName(0) -> portToInstr(0) )

  def paramsToInstructionBlock(param: String, whichOne: Int): Instruction = param match {
    case any(_) => Forward(outputPortName(whichOne))
    case none() => if (whichOne < lastIndex)
        portToInstr(whichOne + 1)
      else
        NoOp
    case _ => {
      val conditions = param.split(conditionSeparator).toList

      def conditionsToInstruction(conds: List[String]): Instruction = {
        val cond = conds.head
        If(conditionToConstraint(cond),
          if (conds.length == 1)
            Forward(outputPortName(whichOne))
          else
            conditionsToInstruction(conds.tail),
          if (whichOne < lastIndex)
            portToInstr(whichOne + 1)
          else
            NoOp
        )
      }

      conditionsToInstruction(conditions)
    }
  }

}

class IPClassifierElementBuilder(name: String)
  extends ElementBuilder(name, "IPClassifier") {

  addInputPort(Port())

  override def buildElement: GenericElement = {
    new IPClassifier(name, getInputPorts, getOutputPorts, getConfigParameters)
  }

  override def handleConfigParameter(paramString: String): ElementBuilder = {
    super.handleConfigParameter(paramString)
    addOutputPort(Port(Some(paramString)))
  }
}

object IPClassifier {

  private var unnamedCount = 0

  private val genericElementName = "IPClassifier"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPClassifierElementBuilder = {
    increment ; new IPClassifierElementBuilder(name)
  }

  def getBuilder: IPClassifierElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}