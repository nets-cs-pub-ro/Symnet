package clickfiletoexecutor.multipleclicks

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.parser.interclicklinks.InterClickLinksParser
import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.scalatest.{FlatSpec, Matchers}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class ExecuteMultipleClicks extends FlatSpec with Matchers {

  "Multiple click files" should "produce a valid execution context" in {
    val ctx = ClickExecutionContext.buildAggregated(
      List(
        "src/main/resources/click_test_files/multiple_files/trivial/a.click",
        "src/main/resources/click_test_files/multiple_files/trivial/b.click",
        "src/main/resources/click_test_files/multiple_files/trivial/c.click"
      ).map(ClickToAbstractNetwork.buildConfig(_, prefixedElements = true)),
      InterClickLinksParser.parseLinks("src/main/resources/click_test_files/multiple_files/trivial/abc.links")
    )

    ctx shouldBe a [ClickExecutionContext]
    ctx.instructions should have size 6
    ctx.links should have size 5
  }

}
