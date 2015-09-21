package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.expression.concrete.nonprimitive.{Address, :@, Symbol}
import org.change.v2.analysis.memory.TagExp
import org.change.v2.analysis.processingmodels.{LocationId, Instruction}
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.util.regexes._
import org.change.v2.util.canonicalnames._
import org.change.v2.util.conversion.RepresentationConversion._

/**
 * radu
 * 3/5/14
 */
class IPRewriter(name: String,
         inputPorts: List[Port],
         outputPorts: List[Port],
         configParams: List[ConfigParameter])
  extends GenericElement(name,
    "IPRewriter",
    inputPorts,
    outputPorts,
    configParams) {

  def installRulesForPattern(whichRule: Int,
                            sp: String, dp: String,
                            sAddr: String, dAddr: String,
                            portA: Int, portB: Int): Instruction = {
    // First installed mapping (forward)
    val fwMapping = InstructionBlock(
      // Check patterns
      AssignNamedSymbol(s"$name-$whichRule-check-sa", Address(IPSrc)),
      AssignNamedSymbol(s"$name-$whichRule-check-da", Address(IPDst)),
      AssignNamedSymbol(s"$name-$whichRule-check-sp", Address(TcpSrc)),
      AssignNamedSymbol(s"$name-$whichRule-check-dp", Address(TcpDst)),
      AssignNamedSymbol(s"$name-$whichRule-check-proto", Address(Proto)),
      // Apply patterns
      AssignNamedSymbol(s"$name-$whichRule-apply-sa", sAddr match {
        case ipv4Regex() => ConstantValue(ipToNumber(sAddr))
        case _ => Address(IPSrc)
      }),
      AssignNamedSymbol(s"$name-$whichRule-apply-da", dAddr match {
        case ipv4Regex() => ConstantValue(ipToNumber(dAddr))
        case _ => Address(IPDst)
      }),
      sp match {
        case numberRegex() => AssignNamedSymbol(s"$name-$whichRule-apply-sp", ConstantValue(sp.toInt))
        case portIntervalRegexWithGroups(l, u) => InstructionBlock(
          AssignNamedSymbol(s"$name-$whichRule-apply-sp", SymbolicValue()),
          ConstrainNamedSymbol(s"$name-$whichRule-apply-sp", :&:(:>=:(ConstantValue(l.toInt)), :<=:(ConstantValue(u.toInt))))
        )
        case _ => AssignNamedSymbol(s"$name-$whichRule-apply-sp", Address(TcpSrc))
      },
      dp match {
        case numberRegex() => AssignNamedSymbol(s"$name-$whichRule-apply-dp", ConstantValue(dp.toInt))
        case portIntervalRegexWithGroups(l, u) => InstructionBlock(
          AssignNamedSymbol(s"$name-$whichRule-apply-dp", SymbolicValue()),
          ConstrainNamedSymbol(s"$name-$whichRule-apply-dp", :&:(:>=:(ConstantValue(l.toInt)), :<=:(ConstantValue(u.toInt))))
        )
        case _ => AssignNamedSymbol(s"$name-$whichRule-apply-dp", Address(TcpDst))
      },
      AssignNamedSymbol(s"$name-$whichRule-apply-fwport", ConstantValue(portA))
    )

    // Second installed mapping (backwards)
    val bkMapping = InstructionBlock(
      // Check patterns
      AssignNamedSymbol(s"$name-${whichRule+1}-check-da", sAddr match {
        case ipv4Regex() => ConstantValue(ipToNumber(sAddr))
        case _ => Address(IPSrc)
      }),
      AssignNamedSymbol(s"$name-${whichRule+1}-check-sa", dAddr match {
        case ipv4Regex() => ConstantValue(ipToNumber(dAddr))
        case _ => Address(IPDst)
      }),
      sp match {
        case numberRegex() => AssignNamedSymbol(s"$name-${whichRule+1}-check-dp", ConstantValue(sp.toInt))
        case portIntervalRegexWithGroups(l, u) => InstructionBlock(
          AssignNamedSymbol(s"$name-${whichRule+1}-check-dp", SymbolicValue()),
          ConstrainNamedSymbol(s"$name-${whichRule+1}-check-dp", :&:(:>=:(ConstantValue(l.toInt)), :<=:(ConstantValue(u.toInt))))
        )
        case _ => AssignNamedSymbol(s"$name-${whichRule+1}-check-dp", Address(TcpSrc))
      },
      dp match {
        case numberRegex() => AssignNamedSymbol(s"$name-${whichRule+1}-check-sp", ConstantValue(dp.toInt))
        case portIntervalRegexWithGroups(l, u) => InstructionBlock(
          AssignNamedSymbol(s"$name-${whichRule+1}-check-sp", SymbolicValue()),
          ConstrainNamedSymbol(s"$name-${whichRule+1}-check-sp", :&:(:>=:(ConstantValue(l.toInt)), :<=:(ConstantValue(u.toInt))))
        )
        case _ => AssignNamedSymbol(s"$name-${whichRule+1}-check-sp", Address(TcpDst))
      },
      AssignNamedSymbol(s"$name-${whichRule+1}-check-proto", Address(Proto)),

      // Apply patterns
      AssignNamedSymbol(s"$name-${whichRule+1}-apply-sa", Address(IPDst)),
      AssignNamedSymbol(s"$name-${whichRule+1}-apply-da", Address(IPSrc)),
      AssignNamedSymbol(s"$name-${whichRule+1}-apply-sp", Address(TcpDst)),
      AssignNamedSymbol(s"$name-${whichRule+1}-apply-dp", Address(TcpSrc)),

      AssignNamedSymbol(s"$name-${whichRule+1}-apply-fwport", ConstantValue(portB))
    )

    InstructionBlock(
      fwMapping,

      bkMapping,

      AssignRaw(IPSrc, Symbol(s"$name-$whichRule-apply-sa")),
      AssignRaw(IPDst, Symbol(s"$name-$whichRule-apply-da")),
      AssignRaw(TcpSrc, Symbol(s"$name-$whichRule-apply-sp")),
      AssignRaw(TcpDst, Symbol(s"$name-$whichRule-apply-dp")),
      // Biggie here
      Forward(outputPortName(portA))
    )
  }

  private def installRulesForKeep(whichRule: Int, portA: Int, portB: Int) = InstructionBlock(
    // Install forward mappings
    AssignNamedSymbol(s"$name-$whichRule-check-sa", Address(IPSrc)),
    AssignNamedSymbol(s"$name-$whichRule-check-da", Address(IPDst)),
    AssignNamedSymbol(s"$name-$whichRule-check-sp", Address(TcpSrc)),
    AssignNamedSymbol(s"$name-$whichRule-check-dp", Address(TcpDst)),
    AssignNamedSymbol(s"$name-$whichRule-check-proto", Address(Proto)),

    AssignNamedSymbol(s"$name-$whichRule-apply-sa", Address(IPSrc)),
    AssignNamedSymbol(s"$name-$whichRule-apply-da", Address(IPDst)),
    AssignNamedSymbol(s"$name-$whichRule-apply-sp", Address(TcpSrc)),
    AssignNamedSymbol(s"$name-$whichRule-apply-dp", Address(TcpDst)),
    AssignNamedSymbol(s"$name-$whichRule-apply-fwport", ConstantValue(portA)),

    // Install reply mappings
    AssignNamedSymbol(s"$name-${whichRule+1}-check-sa", Address(IPDst)),
    AssignNamedSymbol(s"$name-${whichRule+1}-check-da", Address(IPSrc)),
    AssignNamedSymbol(s"$name-${whichRule+1}-check-sp", Address(TcpDst)),
    AssignNamedSymbol(s"$name-${whichRule+1}-check-dp", Address(TcpSrc)),
    AssignNamedSymbol(s"$name-${whichRule+1}-check-proto", Address(Proto)),

    AssignNamedSymbol(s"$name-${whichRule+1}-apply-sa", Address(IPDst)),
    AssignNamedSymbol(s"$name-${whichRule+1}-apply-da", Address(IPSrc)),
    AssignNamedSymbol(s"$name-${whichRule+1}-apply-sp", Address(TcpDst)),
    AssignNamedSymbol(s"$name-${whichRule+1}-apply-dp", Address(TcpSrc)),
    AssignNamedSymbol(s"$name-${whichRule+1}-apply-fwport", ConstantValue(portB)),

    // Forward the flow
    Forward(outputPortName(portA))
  )

  // For every port there will exist an install instruction block.
  private val installInstructions: scala.collection.mutable.Map[String, Instruction] = scala.collection.mutable.Map()

  private var lastCheck = -1

  private val fwPorts: scala.collection.mutable.Map[Int, Int] = scala.collection.mutable.Map()

  def buildCheckAndApplyFor(inputPort: String): Instruction = {
    val buildCache: scala.collection.mutable.Map[Int, Instruction] = scala.collection.mutable.Map()

    def looper(which: Int): Instruction = buildCache.getOrElse(which, {
      val elseInstr = if (which == lastCheck)
        installInstructions(inputPort)
      else
        looper(which + 1)

      val i =
        If(ConstrainRaw(IPSrc, :==:(Symbol(s"$name-$which-check-sa"))),
          If(ConstrainRaw(IPDst, :==:(Symbol(s"$name-$which-check-da"))),
            If(ConstrainRaw(TcpSrc, :==:(Symbol(s"$name-$which-check-sp"))),
              If(ConstrainRaw(TcpDst, :==:(Symbol(s"$name-$which-check-dp"))),
                If(ConstrainRaw(Proto, :==:(Symbol(s"$name-$which-check-proto"))),
                  InstructionBlock(
                    AssignRaw(IPSrc, Symbol(s"$name-$which-apply-sa")),
                    AssignRaw(IPDst, Symbol(s"$name-$which-apply-da")),
                    AssignRaw(TcpSrc, Symbol(s"$name-$which-apply-sp")),
                    AssignRaw(TcpDst, Symbol(s"$name-$which-apply-dp")),
                    // Biggie here
                    Forward(outputPortName(fwPorts(which)))
                  ),
                  elseInstr
                ),
                elseInstr
              ),
              elseInstr
            ),
            elseInstr
          ),
          elseInstr
        )
      buildCache += (which -> i)
      i
    })

    looper(0)
  }

  def buildFullIntructions(inputspec: String, port: Int) = inputspec match {
    case IPRewriter.keepPattern(fwPort, rpPort) => {
      installInstructions += (inputPortName(port) -> installRulesForKeep(port * 2, fwPort.toInt, rpPort.toInt))
      lastCheck += 2
      fwPorts += (2 * port -> fwPort.toInt)
      fwPorts += (2 * port + 1-> rpPort.toInt)
      buildCheckAndApplyFor(inputPortName(port))
    }
    case IPRewriter.rewritePattern(sa, sp, da, dp, fwPort, rpPort) => {
      installInstructions += (inputPortName(port) -> installRulesForPattern(port * 2, sp, dp, sa, da, fwPort.toInt, rpPort.toInt))
      lastCheck += 2
      fwPorts += (2 * port -> fwPort.toInt)
      fwPorts += (2 * port + 1-> rpPort.toInt)
      buildCheckAndApplyFor(inputPortName(port))
    }
    case IPRewriter.passPattern(outputPort) => Forward(outputPortName(outputPort.toInt))
    case "drop" | "discard" => Fail("Flow dropped.")
  }

  private val iCache: scala.collection.mutable.Map[String, Instruction] = scala.collection.mutable.Map()

  private def buildRewriter(): Unit = for (
    (cp, i) <- configParams.zipWithIndex
  ) {
    iCache += (inputPortName(i) -> buildFullIntructions(cp.value, i))
  }

  private var iCacheMap: Map[LocationId, Instruction] = _

  override val instructions = {
    if (iCache.isEmpty) {
      buildRewriter()
      iCacheMap = iCache.toMap
    }

    iCacheMap
  }

  override def outputPortName(which: Int): String = s"$name-out-$which"
  override def inputPortName(which: Int): String = s"$name-in-$which"
}

class IPRewriterElementBuilder(name: String)
  extends ElementBuilder(name, "IPRewriter") {

  override def buildElement: GenericElement = {
    new IPRewriter(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPRewriter {
  val passPattern = ("pass (" + number+ ")").r
  val keepPattern = ("keep (" + number+ ") (" + number+ ")").r
  val addrPattern = ipv4+ "|-"
  val portPattern = number + "\\s*-\\s*" + number + "|-|" + number
  val rewritePattern = ("pattern (" + addrPattern + ") (" + portPattern + ") (" + addrPattern + ") (" +
    portPattern + ") (" + number + ") (" + number + ")").r

  private var unnamedCount = 0

  private val genericElementName = "ipRewriter"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): IPRewriterElementBuilder = {
    increment ; new IPRewriterElementBuilder(name)
  }

  def getBuilder: IPRewriterElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")
}
