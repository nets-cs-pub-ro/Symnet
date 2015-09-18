package clickfiletoexecutor

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class CiscoBehemotSwitch extends FlatSpec with Matchers {

  "The behemot" should "be parseable to a click executor" in {
    val absNet = ClickToAbstractNetwork.buildConfig("src/main/resources/click_test_files/CiscoEtherSwitch.click")
    val executor = ClickExecutionContext.fromSingle(absNet)
  }

}
