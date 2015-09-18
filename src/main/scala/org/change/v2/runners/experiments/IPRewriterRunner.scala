package org.change.v2.runners.experiments

import java.io.{FileOutputStream, PrintWriter}
import java.io.File

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object IPRewriterRunner {

  def main (args: Array[String]) {
    val clickConfig = "src/main/resources/click_test_files/IPRewriter.click"
    val absNet = ClickToAbstractNetwork.buildConfig(clickConfig)
    val executor = ClickExecutionContext.fromSingle(absNet)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute(true)
    }

    val replayExecutor = new ClickExecutionContext(
      crtExecutor.instructions,
      crtExecutor.links,
      List(crtExecutor.stuckStates.head.forwardTo(absNet.entryLocationId)),
      Nil,
      Nil
    )

    crtExecutor = replayExecutor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute(true)
    }

    val pr = new PrintWriter(new FileOutputStream(new File("iprw.output")))

    pr.print(crtExecutor.stringifyStates(includeOk = false, includeFailed = true))
    pr.close()
  }

}
