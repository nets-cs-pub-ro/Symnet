package org.change.v2.abstractnet.optimized.macswitch

import java.io.{InputStreamReader, FileInputStream, File}

import org.change.v2.abstractnet.generic.{ElementBuilder, GenericElement, ConfigParameter, Port}
import org.change.v2.analysis.constraint.{EQ_E, OR}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.processingmodels._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.utils.RepresentationConversion
import org.change.v2.util.canonicalnames._

import scala.io.Source

/**
 * A small gift from radu to symnetic.
 */
class OptimizedSwitch(name: String,
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
    inputPortName(0) -> Fail("Unexpected packet dropped @ " + getName)
  )

  override def outputPortName(which: Int = 0): String = s"$name-$which-out"
}

class OptimizedSwitchElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  override def buildElement: GenericElement = {
    new OptimizedSwitch(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object OptimizedSwitch {
  private var unnamedCount = 0

  private val genericElementName = "Switch"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): OptimizedSwitchElementBuilder = {
    increment ; new OptimizedSwitchElementBuilder(name, "Switch")
  }

  def getBuilder: OptimizedSwitchElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")

  def fromCiscoMacTable(f: File): OptimizedSwitch = {
    val name = f.getName.trim

    val stream = Source.fromFile(f)

    import org.change.v2.util.regexes._
    val instrs: Iterable[Instruction] = (for {
      l <- stream.getLines()
      if (l.matches(".*" + macCisco + ".*"))
      parts = l.trim.split("\\s+")
      vlan = parts(0)
      mac = parts(1)
      port = parts(3)
    } yield {
      (port, vlan, mac)
    }).toTraversable.groupBy({triplet =>  (triplet._1, triplet._2)}).map({kv => {
      val (port, vlan) = kv._1
      val macs = kv._2.map(_._3).map(RepresentationConversion.macToNumberCiscoFormat(_))
      val macConstraint = ConstrainRaw(EtherDst, OR(macs.map(m => EQ_E(ConstantValue(m))).toList))

      If(macConstraint,
        if(vlan equals "All")
          Forward(port)
        else
          If(Constrain(VLANTag, :==:(ConstantValue(vlan.toInt))),
            Forward(port),
            Fail("Cannot Forward, bad VLAN")),
        Fail("Cannot Forward, MacDst doesn't match"))
    }})

    new OptimizedSwitch(name, genericElementName, Nil, Nil, Nil) {
      override def instructions: Map[LocationId, Instruction] = Map("0" -> Fork(instrs))
    }
  }
}
