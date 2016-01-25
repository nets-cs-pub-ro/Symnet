package org.change.v2.executor.clickabstractnetwork

import java.io.File

import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.abstractnet.optimized.macswitch.OptimizedSwitch

/**
 * A small gift from radu to symnetic.
 */
object AggregatedBuilder {

  type Link = (String, String, Int, String, String, Int)
  type Port = (String, String, Int)
  type ConfigParser = File => NetworkConfig

//  def parsers: Map[String, ConfigParser] = Map(
//    "sw" -> OptimizedSwitch.fromCiscoMacTable _
//  )

  def parseInput(rootFolder: File): (Iterable[NetworkConfig], Iterable[Link], Iterable[Port]) = {

    (Nil, Nil, Nil)
  }

}
