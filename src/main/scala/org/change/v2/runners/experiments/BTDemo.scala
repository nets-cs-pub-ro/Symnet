package org.change.v2.runners.experiments

import java.io.File

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.abstractnet.optimized.macswitch.OptimizedSwitch
import org.change.v2.abstractnet.optimized.router.OptimizedRouter
import org.change.v2.executor.clickabstractnetwork.executionlogging.JsonLogger
import org.change.v2.executor.clickabstractnetwork.AggregatedBuilder._

/**
  * This is an example of how to analyze a heterogeneous data plane.
  *
  * It can parse and analyze routers, switches and arbitrary Click files.
  *
  * The path to the folder that includes the data plane specification is the first parameter of the main
  * command line parameter. If one is not provided, then it defaults to "src/main/resources/21cn"
  */
object BTDemo {

  def main(args: Array[String]): Unit = {

    val dataPlaneFolder = if (args.length > 2) args(1) else "src/main/resources/21cn"

    val exe = executorFromFolder(new File(dataPlaneFolder), Map(
      "switch" -> OptimizedSwitch.trivialSwitchNetworkConfig _,
      "click" -> {f => ClickToAbstractNetwork.buildConfig(f, prefixedElements = true)},
      "router" -> OptimizedRouter.trivialRouterNetwrokConfig _
    )).setLogger(JsonLogger)

    val start = System.currentTimeMillis()
        exe.untilDone(true)
    println("It took me:" + (System.currentTimeMillis() - start))
  }

}
