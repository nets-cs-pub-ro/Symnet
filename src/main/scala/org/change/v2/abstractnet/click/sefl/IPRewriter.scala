package org.change.v2.abstractnet.click.sefl

import org.change.v2.abstractnet.generic.{ConfigParameter, ElementBuilder, GenericElement, Port}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.util.regexes._
import org.change.v2.util.canonicalnames._

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

  private val passPattern = ("pass (" + number+ ")").r
  private val keepPattern = ("keep (" + number+ ") (" + number+ ")").r

  private def installKeepPatterns(whichRule: Int, portA: Int, portB: Int) = InstructionBlock(
    // Install forward mappings
    Assign(s"$name-$whichRule-check-sa", :@(IPSrc)),
    Assign(s"$name-$whichRule-check-da", :@(IPDst)),
    Assign(s"$name-$whichRule-check-sp", :@(PortSrc)),
    Assign(s"$name-$whichRule-check-dp", :@(PortDst)),
    Assign(s"$name-$whichRule-check-proto", :@(L4Proto)),

    Assign(s"$name-$whichRule-apply-sa", :@(IPSrc)),
    Assign(s"$name-$whichRule-apply-da", :@(IPDst)),
    Assign(s"$name-$whichRule-apply-sp", :@(PortSrc)),
    Assign(s"$name-$whichRule-apply-dp", :@(PortDst)),
    Assign(s"$name-$whichRule-apply-fwport", ConstantValue(portA)),
    // Install reply mappings
    Assign(s"$name-${whichRule+1}-check-sa", :@(IPDst)),
    Assign(s"$name-${whichRule+1}-check-da", :@(IPSrc)),
    Assign(s"$name-${whichRule+1}-check-sp", :@(PortDst)),
    Assign(s"$name-${whichRule+1}-check-dp", :@(PortSrc)),
    Assign(s"$name-${whichRule+1}-check-proto", :@(L4Proto)),

    Assign(s"$name-${whichRule+1}-apply-sa", :@(IPDst)),
    Assign(s"$name-${whichRule+1}-apply-da", :@(IPSrc)),
    Assign(s"$name-${whichRule+1}-apply-sp", :@(PortDst)),
    Assign(s"$name-${whichRule+1}-apply-dp", :@(PortSrc)),
    Assign(s"$name-${whichRule+1}-apply-fwport", ConstantValue(portB)),
    // Forward the flow
    Forward(outputPortName(portA))
  )

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


      val i = If(Constrain(IPSrc, :==:(:@(s"$name-$which-check-sa"))),
          If(Constrain(IPDst, :==:(:@(s"$name-$which-check-da"))),
            If(Constrain(PortSrc, :==:(:@(s"$name-$which-check-sp"))),
              If(Constrain(PortDst, :==:(:@(s"$name-$which-check-dp"))),
                If(Constrain(L4Proto, :==:(:@(s"$name-$which-check-proto"))),
                  InstructionBlock(
                    Assign(IPSrc, :@(s"$name-$which-apply-sa")),
                    Assign(IPDst, :@(s"$name-$which-apply-da")),
                    Assign(PortSrc, :@(s"$name-$which-apply-sp")),
                    Assign(PortDst, :@(s"$name-$which-apply-dp")),
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

  def buildSpecIntructions(inputspec: String, port: Int) = inputspec match {
    case keepPattern(fwPort, rpPort) => {
      installInstructions += (inputPortName(port) -> installKeepPatterns(port * 2, fwPort.toInt, rpPort.toInt))
      lastCheck += 2
      fwPorts += (2 * port -> fwPort.toInt)
      fwPorts += (2 * port + 1-> rpPort.toInt)
      buildCheckAndApplyFor(inputPortName(port))
    }
    case passPattern(outputPort) => Forward(outputPortName(outputPort.toInt))
    case "drop" | "discard" => NoOp
  }

  private val iCache: scala.collection.mutable.Map[String, Instruction] = scala.collection.mutable.Map()

  for (
    (cp, i) <- configParams.zipWithIndex
  ) {
    iCache += (inputPortName(i) -> buildSpecIntructions(cp.value, i))
  }

  override val instructions = iCache.toMap

  override def outputPortName(which: Int): String = s"$name-out-$which"
  override def inputPortName(which: Int): String = s"$name-in-$which"
}

class IPRewriterElementBuilder(name: String)
  extends ElementBuilder(name, "IPRewriter") {

  addInputPort(Port())
  addOutputPort(Port())

  override def buildElement: GenericElement = {
    new IPRewriter(name, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object IPRewriter {
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
