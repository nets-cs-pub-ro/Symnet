package org.change.v2.abstractnet.optimizedrouter

import org.change.v2.analysis.constraint.{OR, LTE_E, GTE_E, AND, NOT}
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.memory.State
import org.change.v2.analysis.processingmodels.instructions.{:>:, ConstrainRaw}
import org.change.v2.util.regexes._
import org.change.v2.util.conversion.RepresentationConversion
import org.change.v2.util.canonicalnames._
import org.change.v2.analysis.processingmodels.Instruction

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
    //    val rsmall = makeRouter(getRoutingEntries(small))
    val stopa = System.currentTimeMillis()
    //    val afterSmall = rsmall.map(kv => kv._2.apply(state)).map(_._1).toList.sortBy(_.length)
    //    val perPort = afterSmall(afterSmall.length / 2)
    //    val stateToHuge = perPort.head
    val stopb = System.currentTimeMillis()
    //    val rhuge = makeRouter(getRoutingEntries(huge))
    val stopc = System.currentTimeMillis()

    //    for {
    //      (p, i) <- rhuge
    //    } i(stateToHuge)

    //    val stopd = System.currentTimeMillis()
    //    println(stopa - start + stopc - stopb)
    //    println(stopb - stopa + stopd - stopc)
    //  }
  }
}
