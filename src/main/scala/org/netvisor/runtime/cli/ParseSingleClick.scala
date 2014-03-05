package org.netvisor.runtime.cli

import java.io.FileInputStream
import java.io.File
import org.netvisor.parser.abstractnet.ClickToAbstractNetwork

/**
 * radu
 * 3/5/14
 */
object ParseSingleClick {

  def main(args: Array[String]) {

    val inputFile = new File(args(0))
    val networkAbstract = ClickToAbstractNetwork.buildConfig(inputFile)

    println(networkAbstract)

    println(List.fill(15)("=").mkString(""))

    println(networkAbstract.asHaskellWithRuleNumber())
  }

}
