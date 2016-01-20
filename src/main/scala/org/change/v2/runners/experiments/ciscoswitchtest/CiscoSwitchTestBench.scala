package org.change.v2.runners.experiments.ciscoswitchtest

import java.io.{FileOutputStream, PrintWriter, File}
import org.change.v2.analysis.memory.State

/**
 * A small gift from radu to symnetic.
 */
object CiscoSwitchTestBench extends App {

  import org.change.v2.abstractnet.optimized.macswitch.OptimizedSwitch
  val sw = OptimizedSwitch.fromCiscoMacTableIgnoringVlans(new File("src/main/resources/full_facultatea/sw-ef00.txt"))
  val start = System.currentTimeMillis()

  val r = sw.instructions.head._2.apply(State.allSymbolic, true)

  val stop = System.currentTimeMillis()

  println(stop - start)

  println(r._1.length)
  println(r._2.length)

  val f = new PrintWriter(new FileOutputStream(new File("switch.json")))
  for (s <- r._1) {
    f.println(s.jsonString)
  }
}
