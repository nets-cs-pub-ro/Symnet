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


  /**
   * The method takes an atomic tcpdump condition and creates it's associated constraint.
   * @param condition
   * @return
   */
  private def conditionToConstraint(condition: String): Instruction = condition match {
    case IPClassifier.color(v) => ConstrainNamedSymbol(Paint.COLOR, :==:(ConstantValue(v.toInt)))

    case IPClassifier.ipProto(v) => ConstrainRaw(IPVersion, :==:(ConstantValue(v.toInt)))

    case IPClassifier.srcHostAddr(ip) => ConstrainRaw(IPSrc, :==:(ConstantValue(ipToNumber(ip))))
    case IPClassifier.dstHostAddr(ip) => ConstrainRaw(IPDst, :==:(ConstantValue(ipToNumber(ip))))

    case IPClassifier.etherSrc(macSrc) => ConstrainRaw(EtherSrc, :==:(ConstantValue(macToNumberCiscoFormat(macSrc))))
    case IPClassifier.etherDst(macDst) => ConstrainRaw(EtherDst, :==:(ConstantValue(macToNumberCiscoFormat(macDst))))

    case IPClassifier.dstNetAddr(ip, mask) => {
      val (lower, upper) = ipAndMaskToInterval(ip, mask)
      ConstrainRaw(IPDst, :&:(:>=:(ConstantValue(lower)), :<=:(ConstantValue(upper))))
    }
    case IPClassifier.dstNetExplicitAddr(ip, mask) => {
      val (lower, upper) = ipAndExplicitMaskToInterval(ip, mask)
      ConstrainRaw(IPDst, :&:(:>=:(ConstantValue(lower)), :<=:(ConstantValue(upper))))
    }
    case IPClassifier.srcNetAddr(ip, mask) => {
      val (lower, upper) = ipAndMaskToInterval(ip, mask)
      ConstrainRaw(IPSrc, :&:(:>=:(ConstantValue(lower)), :<=:(ConstantValue(upper))))
    }
    case IPClassifier.srcNetExplicitAddr(ip, mask) => {
      val (lower, upper) = ipAndExplicitMaskToInterval(ip, mask)
      ConstrainRaw(IPSrc, :&:(:>=:(ConstantValue(lower)), :<=:(ConstantValue(upper))))
    }

    case IPClassifier.srcPort(_, port) => ConstrainRaw(TcpSrc, :==:(ConstantValue(port.toInt)))
    case IPClassifier.dstPort(_, port) => ConstrainRaw(TcpDst, :==:(ConstantValue(port.toInt)))

    case IPClassifier.tcp() => ConstrainRaw(Proto, :==:(ConstantValue(TCPProto)))
    case IPClassifier.udp() => ConstrainRaw(Proto, :==:(ConstantValue(UDPProto)))

  }

  val portToInstr = scala.collection.mutable.Map[Int, Instruction]()

  /**
   * The construction of instructions from config params works backwards since the i-th
   * if needs the i+1-th if as its the else branch.
   */
  private def buildClassifier(): Unit = for {
    (p,i) <- configParams.zipWithIndex.reverse
  } {
    portToInstr += ((i, paramsToInstructionBlock(p.value,i)))
  }

  override def instructions: Map[LocationId, Instruction] = {
    // Build it first
    if (portToInstr.isEmpty) buildClassifier() else ()
    // Return it later
    Map( inputPortName(0) -> portToInstr(0) )
  }

  def paramsToInstructionBlock(param: String, whichOne: Int): Instruction = param match {
    case IPClassifier.any(_) => Forward(outputPortName(whichOne))

    case IPClassifier.none() => if (whichOne < lastIndex)
//      If the none/false condition is found, then nothing is processed here, the next instruction
//      gets executed instead.
      portToInstr(whichOne + 1)
//      Otherwise, nothing is done here
      else
        Fail(IPClassifier.failErrorMessage)

//      Conversion of tcpdump rules
    case _ => {
      val conditions = param.split(IPClassifier.conditionSeparator).toList

      def conditionsToInstruction(conds: List[String]): Instruction = {
        val cond = conds.head
        If(conditionToConstraint(cond),
          // then branch (if condition is met)
          if (conds.length == 1)
            Forward(outputPortName(whichOne))
          else
            conditionsToInstruction(conds.tail),
          // else branch,
          if (whichOne < lastIndex)
            portToInstr(whichOne + 1)
          else
            Fail(IPClassifier.failErrorMessage)
        )
      }

      conditionsToInstruction(conditions)
    }
  }

  override def outputPortName(which: Int): String = s"$getName-out-$which"
}

class IPClassifierElementBuilder(name: String)
  extends ElementBuilder(name, "IPClassifier") {

  addInputPort(Port())

  override def buildElement: GenericElement = {
    new IPClassifier(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPClassifier {
  // Supported condition formats.
  val conditionSeparator = """\s+(and|&&)\s+"""

  val color = ("paint color (" + number + ")").r

  val ipProto = ("ip proto (" + number + ")").r

  val srcHostAddr = ("src host (" + ipv4 + ")").r
  val srcNetAddr = ("src net (" + ipv4 + ")/(" + number + ")").r
  val srcNetExplicitAddr = ("src net (" + ipv4 + ") mask (" + ipv4 + ")").r

  val dstHostAddr = ("dst host (" + ipv4 + ")").r
  val dstNetAddr = ("dst net (" + ipv4 + ")/(" + number + ")").r
  val dstNetExplicitAddr = ("dst net (" + ipv4 + ") mask (" + ipv4 + ")").r

  val srcPort = ("src (tcp|udp) port (" + number + ")").r
  val dstPort = ("dst (tcp|udp) port (" + number + ")").r

  val etherSrc = ("ether src (" + macCisco +")").r
  val etherDst = ("ether dst (" + macCisco +")").r

  val tcp = "tcp".r
  val udp = "udp".r

  val any = """\s*(true|\-)\s*""".r
  val none = """\s*false\s*""".r

  val failErrorMessage = "No other alternative output port remaining."

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