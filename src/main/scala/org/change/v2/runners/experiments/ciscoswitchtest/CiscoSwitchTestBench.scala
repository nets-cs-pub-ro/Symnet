package org.change.v2.runners.experiments.ciscoswitchtest

import java.io.{FileOutputStream, PrintWriter, File}
import org.change.v2.analysis.memory.State
import org.change.v2.analysis.memory.MemorySpace

/**
 * A small gift from radu to symnetic.
 */
object CiscoSwitchTestBench extends App {

  import org.change.v2.abstractnet.optimized.macswitch.OptimizedSwitch

  def bench(switchMaker: File => OptimizedSwitch): Unit = {
    val sw = switchMaker(new File("src/main/resources/full_facultatea/switch_input.txt"))
    val z3Start = MemorySpace.z3Time
    val z3Call = MemorySpace.z3Call
    val start = System.currentTimeMillis()

    val r = sw.instructions.head._2.apply(State.allSymbolic, true)

    val stop = System.currentTimeMillis()

    println("Took:" + (stop - start) + " Z3 time "+ (MemorySpace.z3Time-z3Start)+ " calls "+(MemorySpace.z3Call-z3Call))

    println("Successful " + r._1.length)
    println("Failed " + r._2.length)
  }

  println("Best")
  bench(OptimizedSwitch.fromCiscoMacTableIgnoringVlans)
  println("Linear, but grouped macs per port")
  bench(OptimizedSwitch.unoptimizedGroupedLinearSwitch)
  println("Linear lookup")
  bench(OptimizedSwitch.unoptimizedLinearLookupSwitch)



//  val f = new PrintWriter(new FileOutputStream(new File("switch.json")))
//  for (s <- r._1) {
//    f.println(s.jsonString)
//  }
}
