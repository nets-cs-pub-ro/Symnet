package org.change.v2.abstractnet.optimized.router

import org.change.utils
import org.change.v2.abstractnet.generic._
import org.change.v2.analysis.constraint._
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.processingmodels._
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.util.canonicalnames._
import java.io.File
import scala.io.Source
import scala.util.matching.Regex
import org.change.v2.util.regexes._
import org.change.v2.util.conversion.RepresentationConversion

/**
 * A small gift from radu to symnetic.
 */
class OptimizedRouter(name: String,
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

class OptimizedRouterElementBuilder(name: String, elementType: String)
  extends ElementBuilder(name, elementType) {

  override def buildElement: GenericElement = {
    new OptimizedRouter(name, elementType, getInputPorts, getOutputPorts, getConfigParameters)
  }
}

object OptimizedRouter {
  private var unnamedCount = 0

  private val genericElementName = "Router"

  private def increment {
    unnamedCount += 1
  }

  def getBuilder(name: String): OptimizedRouterElementBuilder = {
    increment;
    new OptimizedRouterElementBuilder(name, "Router")
  }

  def getBuilder: OptimizedRouterElementBuilder =
    getBuilder(s"$genericElementName-$unnamedCount")

  def getRoutingEntries(file: File): Seq[((Long, Long), String)] = {
    (for {
      line <- scala.io.Source.fromFile(file).getLines()
      tokens = line.split("\\s+")
      if (tokens.length >= 3)
      if tokens(0) != ""
      matchPattern = tokens(0)
      forwardingPort = tokens(2)
    } yield (
        matchPattern match {
          case ipv4netmaskRegex() => {
            val netMaskTokens = matchPattern.split("/")
            val netAddr = netMaskTokens(0)
            val mask = netMaskTokens(1)
            val (l, u) = RepresentationConversion.ipAndMaskToInterval(netAddr, mask)
            (l,u)
          }
        },
        forwardingPort
        )).toSeq.sortBy(i => i._1._2 - i._1._1)
  }

  def makeRouter(f: File): OptimizedRouter = {
    val table = getRoutingEntries(f)
    val name = f.getName.trim.stripSuffix(".rt")

    new OptimizedRouter(name + "-" + name,"Router", Nil, Nil, Nil) {
      override def instructions: Map[LocationId, Instruction] = Map(inputPortName("port") ->
        Fork(table.map(i => {
          val ((l,u), port) = i
          (port, AND(List(GTE_E(ConstantValue(l)), LTE_E(ConstantValue(u))) ++
            {
              val conflicts = table.takeWhile(i =>  u-l > i._1._2 - i._1._1)filter( other => {
                val ((otherL, otherU), otherPort) = other
                port != otherPort &&
                  l <= otherL &&
                  u >= otherU
              })

              if (conflicts.nonEmpty)
                Seq(NOT(OR((conflicts.map( conflictual => {
                  AND(List(GTE_E(ConstantValue(conflictual._1._1)), LTE_E(ConstantValue(conflictual._1._2))))
                }).toList))))
              else Nil
            }))
        }).groupBy(_._1).map( kv =>
          InstructionBlock(ConstrainRaw(IPDst, OR(kv._2.map(_._2).toList)), Forward(outputPortName(kv._1)))
          )))
    }
  }

  def makeNaiveRouter(f: File): OptimizedRouter = {
    val table = getRoutingEntries(f)

    var conflictCount = 0L
    var which = 0

    val i = table.map(i => {
      val ((l,u), port) = i
      (port, AND(List(GTE_E(ConstantValue(l)), LTE_E(ConstantValue(u))) ++
        {
          val conflicts = table.takeWhile(i =>  u-l > i._1._2 - i._1._1)filter( other => {
            val ((otherL, otherU), otherPort) = other
            port != otherPort &&
              l <= otherL &&
              u >= otherU
          })

          conflictCount += conflicts.size
          which += 1

          if (which % 100 == 0) println(which)

          if (conflicts.nonEmpty)
            Seq(NOT(OR((conflicts.map( conflictual => {
              AND(List(GTE_E(ConstantValue(conflictual._1._1)), LTE_E(ConstantValue(conflictual._1._2))))
            }).toList))))
          else Nil
        }))
    }).groupBy(_._1).foldRight(Fail("No route"): Instruction)( (kv, a) =>
      If(ConstrainRaw(IPDst, OR(kv._2.map(_._2).toList)), Forward(kv._1), a)
    )

    println("Routing table size " + table.length)
    println("Conflict count " + conflictCount)

    new OptimizedRouter(f.getName,"Router", Nil, Nil, Nil) {
      override def instructions: Map[LocationId, Instruction] = Map("0" -> i)
    }
  }

  def optimizedRouterNetworkConfig(f: File): NetworkConfig = {
    val elem = makeRouter(f)

    NetworkConfig(Some(f.getName.trim.stripSuffix(".rt")), Map((elem.getName) -> elem), Nil)
  }
}
