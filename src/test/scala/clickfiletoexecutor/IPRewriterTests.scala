package clickfiletoexecutor

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class IPRewriterTests extends FlatSpec with Matchers {

  "An executor context" should "be built from a click having and IPRewriter" in {
    val clickConfig = "src/main/resources/click_test_files/IPRewriter.click"
    val absNet = ClickToAbstractNetwork.buildConfig(clickConfig)
    val executor = ClickExecutionContext(absNet)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    crtExecutor.stuckStates should have length (1)

    val replayExecutor = new ClickExecutionContext(
      crtExecutor.instructions,
      crtExecutor.links,
      List(crtExecutor.stuckStates.head.forwardTo(absNet.entryLocationId)),
      Nil,
      Nil
    )

    crtExecutor = replayExecutor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }
  }

}