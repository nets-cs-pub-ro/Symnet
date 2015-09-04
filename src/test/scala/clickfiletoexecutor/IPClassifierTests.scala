package clickfiletoexecutor

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class IPClassifierTests  extends FlatSpec with Matchers {

  "A src-classif-dst click" should "classify correctly" in {
    val absNet = ClickToAbstractNetwork.buildConfig("src/main/resources/click_test_files/Classif.click")
    val executor = ClickExecutionContext(absNet)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    crtExecutor.stuckStates should have length (2)
  }



}