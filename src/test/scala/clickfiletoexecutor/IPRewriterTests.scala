package clickfiletoexecutor

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.abstractnet.click.sefl.IPRewriter
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class IPRewriterTests extends FlatSpec with Matchers {

  "An executor context" should "be built from a click having an IPRewriter" in {
    val clickConfig = "src/main/resources/click_test_files/IPRewriter.click"
    val absNet = ClickToAbstractNetwork.buildConfig(clickConfig)
    val executor = ClickExecutionContext.fromSingle(absNet)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    crtExecutor.stuckStates should have length 1

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

  "IPRewriter patterns" should "match valid input patterns" in {
    assert("pattern 1.0.0.0 1 10.0.0.1 2 0 1" match {
      case IPRewriter.rewritePattern(_,_,_,_,_,_) => true
      case _ => false
    })

    assert("pattern 1.0.0.0 1-2 10.0.0.1 2-15 0 1" match {
      case IPRewriter.rewritePattern(_,_,_,_,_,_) => true
      case _ => false
    })

    assert("pattern - - - - 0 1" match {
      case IPRewriter.rewritePattern(_,_,_,_,_,_) => true
      case _ => false
    })

    assert("pattern - 1-10 - 14-20 0 1" match {
      case IPRewriter.rewritePattern(_,_,_,_,_,_) => true
      case _ => false
    })
  }

  "An executor context" should "be built from a click having an IPRewriter with rewrite patterns" in {
    val clickConfig = "src/main/resources/click_test_files/IPRewriter2.click"
    val absNet = ClickToAbstractNetwork.buildConfig(clickConfig)
    val executor = ClickExecutionContext.fromSingle(absNet)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    crtExecutor.stuckStates should have length (1)
  }

}