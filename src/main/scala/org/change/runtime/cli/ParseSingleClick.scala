package org.change.runtime.cli

import java.io.File
import org.change.parser.abstractnet.ClickToAbstractNetwork
import org.change.symbolicexec.{Input, PathLocation, SymbolicExecutor, Path}
import org.change.symbolicexec.executorhooks._

object ParseSingleClick {

  def main(args: Array[String]) {
    if (args.length < 1) {
      println("Usage: <click file> [haskell|symb]")
    }

    val inputFile = new File(args(0))
    val option = if (args.length > 1 ) args(1) else "haskell"

    val networkAbstract = ClickToAbstractNetwork.buildConfig(inputFile)

    option match {
      case "haskell" => println(networkAbstract.asHaskellWithRuleNumber())
      case "symb" => {
        val executor = new SymbolicExecutor(networkAbstract)
        println(executor.execute(noopHook)(List(Path().modifyAndMove({m =>
        m.newVal("IP-Src").newVal("IP-Dst").newVal("Port-Src").newVal("Port-Dst")},
        PathLocation("base_click-src", 0, Input)))))
      }
    }

  }

}
