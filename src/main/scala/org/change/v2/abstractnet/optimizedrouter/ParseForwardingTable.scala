package org.change.v2.abstractnet.optimizedrouter

import org.change.v2.analysis.constraint.{OR, LTE_E, GTE_E, AND, NOT}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.processingmodels.instructions.{:>:, ConstrainRaw}
import org.change.v2.util.regexes._
import org.change.v2.util.conversion.RepresentationConversion
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.processingmodels.{Instruction, State}

/**
 * Created by radu on 24.09.2015.
 */
object ParseForwardingTable {

  type RoutingEntry = (String, String)

  val huge = "/0/projects/internal/Symnetic/src/main/resources/routing_tables/huge.txt"
  val small = "/0/projects/internal/Symnetic/src/main/resources/routing_tables/small.txt"

  def main(args: Array[String]): Unit = {
    val state = State.bigBang
    val start = System.currentTimeMillis()
    val rsmall = makeRouter(getRoutingEntries(small))
    val stopa = System.currentTimeMillis()
    val afterSmall = rsmall.map(kv => kv._2.apply(state)).map(_._1).toList.sortBy(_.length)
    val perPort = afterSmall(afterSmall.length / 2)
    val stateToHuge = perPort.head
    val stopb = System.currentTimeMillis()
    val rhuge = makeRouter(getRoutingEntries(huge))
    val stopc = System.currentTimeMillis()

    for {
      (p, i) <- rhuge
    } i(stateToHuge)

    val stopd = System.currentTimeMillis()
    println(stopa - start + stopc - stopb)
    println(stopb - stopa + stopd - stopc)
  }

  def getRoutingEntries(file: String): List[((Long, Long), String)] = {
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
        )).toList.sortBy(i => i._1._2 - i._1._1)
  }

  def makeRouter(table: List[((Long, Long), String)]): Map[String, Instruction] = {
    table.map(i => {
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
            List(NOT(OR((conflicts.map( conflictual => {
              AND(List(GTE_E(ConstantValue(conflictual._1._1)), LTE_E(ConstantValue(conflictual._1._2))))
            })))))
          else Nil
        }))
    }).groupBy(_._1).map( kv =>
      (kv._1, ConstrainRaw(IPSrc, :>:(:@("a")), Some(OR(kv._2.map(_._2)))))
      )
  }

  def makeNaiveRouter(table: List[((Long, Long), String)]): List[Instruction] =
    table.map(i => {
      val ((l,u), port) = i
      ConstrainRaw(IPSrc, :>:(:@("a")), Some(AND(List(GTE_E(ConstantValue(l)), LTE_E(ConstantValue(u))) ++
        {
          val conflicts = table.takeWhile(i =>  u-l > i._1._2 - i._1._1)filter( other => {
            val ((otherL, otherU), otherPort) = other
            port != otherPort &&
              l <= otherL &&
              u >= otherU
          })

          if (conflicts.nonEmpty)
            List(NOT(OR((conflicts.map( conflictual => {
              AND(List(GTE_E(ConstantValue(conflictual._1._1)), LTE_E(ConstantValue(conflictual._1._2))))
            })))))
          else Nil
        })))
    })
}
