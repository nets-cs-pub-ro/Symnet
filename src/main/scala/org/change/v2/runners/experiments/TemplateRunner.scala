package org.change.v2.runners.experiments

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContextBuilder

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object TemplateRunner {

  def main (args: Array[String]) {
    val clickConfig = "src/main/resources/click_test_files/Template.click"
    val absNet = ClickToAbstractNetwork.buildConfig(clickConfig)
    val executor = ClickExecutionContextBuilder.buildExecutionContext(absNet)

    var crtExecutor = executor
    while (!crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute(verbose = true)
    }

    println(crtExecutor.stringifyStates())

  }

}
