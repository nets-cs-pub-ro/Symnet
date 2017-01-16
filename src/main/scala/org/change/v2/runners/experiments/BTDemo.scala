package org.change.v2.runners.experiments

import java.io.File

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.abstractnet.optimized.macswitch.OptimizedSwitch
import org.change.v2.abstractnet.optimized.router.OptimizedRouter
import org.change.v2.executor.clickabstractnetwork.executionlogging.JsonLogger
import org.change.v2.executor.clickabstractnetwork.AggregatedBuilder._

/**
  * Created by radu on 15.01.2017.
  */
object BTDemo {

  def main(args: Array[String]): Unit = {
    val exe = executorFromFolder(new File("src/main/resources/21cn"), Map(
      "switch" -> OptimizedSwitch.trivialSwitchNetworkConfig _,
      "click" -> {f => ClickToAbstractNetwork.buildConfig(f, prefixedElements = true)},
      "router" -> OptimizedRouter.trivialRouterNetwrokConfig _
    )).setLogger(JsonLogger)

//    val start = System.currentTimeMillis()
        exe.untilDone(false)
//    println(System.currentTimeMillis() - start)
  }

}
