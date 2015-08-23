package clickfiletoexecutor

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.executor.clickabstractnetwork.{ClickExecutionContext, ClickExecutionContextBuilder}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class IPClassifierTests  extends FlatSpec with Matchers {

  "A src-classif-dst click" should "classify correctly" in {
    val absNet = ClickToAbstractNetwork.buildConfig("src/main/resources/click_test_files/Classif.click")
    val executor = ClickExecutionContextBuilder.buildExecutionContext(absNet)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute
    }

    crtExecutor.stuckStates should have length (2)
  }



}