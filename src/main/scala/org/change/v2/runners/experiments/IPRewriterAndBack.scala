package org.change.v2.runners.experiments

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object IPRewriterAndBack {

  val clickConfig = "src/main/resources/click_test_files/IPRewriter2.click"
  val absNet = ClickToAbstractNetwork.buildConfig(clickConfig)
  val executor = ClickExecutionContext.fromSingle(absNet)

  var crtExecutor = executor.untilDone(true)

  val stuck = crtExecutor.stuckStates.head.forwardTo("src-in")

  crtExecutor = new ClickExecutionContext(
    crtExecutor.instructions,
    crtExecutor.links,
    List(stuck),
    Nil,
    Nil,
    Map.empty
  ).untilDone(true)

  //    This actually works. Hand tested :))
  crtExecutor.stringifyStates()

}
