package clickfiletoexecutor.multipleclicks

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.parser.interclicklinks.InterClickLinksParser
import org.change.symbolicexec.verification.RuleSetBuilder
import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.scalatest.{FlatSpec, Matchers}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class ExecuteMultipleClicksTest extends FlatSpec with Matchers {

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

  "Multiple click files with traffic rules" should "produce a valid execution context" in {
    val ctx = ClickExecutionContext.buildAggregated(
      List(
        "src/main/resources/click_test_files/multiple_files/trivial/a.click",
        "src/main/resources/click_test_files/multiple_files/trivial/b.click",
        "src/main/resources/click_test_files/multiple_files/trivial/c.click"
      ).map(ClickToAbstractNetwork.buildConfig(_, prefixedElements = true)),
      InterClickLinksParser.parseLinks("src/main/resources/click_test_files/multiple_files/trivial/abc.links"),
      RuleSetBuilder.buildRuleSetFromFile("src/main/resources/spec_lang_tests/multiple_simple")
    )

    ctx shouldBe a [ClickExecutionContext]
    ctx.instructions should have size 6
    ctx.links should have size 5
    ctx.checkInstructions should have size 3
  }

  "Aggregated executor" should "execute correctly, accross multiple clicks" in {
    val ctx = ClickExecutionContext.buildAggregated(
      List(
        "src/main/resources/click_test_files/multiple_files/trivial/a.click",
        "src/main/resources/click_test_files/multiple_files/trivial/b.click",
        "src/main/resources/click_test_files/multiple_files/trivial/c.click"
      ).map(ClickToAbstractNetwork.buildConfig(_, prefixedElements = true)),
      InterClickLinksParser.parseLinks("src/main/resources/click_test_files/multiple_files/trivial/abc.links"),
      RuleSetBuilder.buildRuleSetFromFile("src/main/resources/spec_lang_tests/multiple_simple")
    )

    var crtExecutor = ctx
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    ctx shouldBe a [ClickExecutionContext]
    ctx.instructions should have size 6
    ctx.links should have size 5
    ctx.checkInstructions should have size 3

    crtExecutor.stuckStates should have length 1
    crtExecutor.okStates should have length 0
    crtExecutor.failedStates should have length 0
  }

  "Aggregated executor" should "execute correctly, accross multiple clicks, even with failing conditions" in {
    val ctx = ClickExecutionContext.buildAggregated(
      List(
        "src/main/resources/click_test_files/multiple_files/trivial/a.click",
        "src/main/resources/click_test_files/multiple_files/trivial/b.click",
        "src/main/resources/click_test_files/multiple_files/trivial/c.click"
      ).map(ClickToAbstractNetwork.buildConfig(_, prefixedElements = true)),
      InterClickLinksParser.parseLinks("src/main/resources/click_test_files/multiple_files/trivial/abc.links"),
      RuleSetBuilder.buildRuleSetFromFile("src/main/resources/spec_lang_tests/multiple_failing_simple")
    )

    var crtExecutor = ctx
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    ctx shouldBe a [ClickExecutionContext]
    ctx.instructions should have size 6
    ctx.links should have size 5
    ctx.checkInstructions should have size 3

    crtExecutor.stuckStates should have length (0)
    crtExecutor.okStates should have length (0)
    crtExecutor.failedStates should have length (1)
  }

  "Aggregated executor" should "execute correctly, accross multiple clicks, even with cascading failing conditions" in {
    val ctx = ClickExecutionContext.buildAggregated(
      List(
        "src/main/resources/click_test_files/multiple_files/trivial/a.click",
        "src/main/resources/click_test_files/multiple_files/trivial/b.click",
        "src/main/resources/click_test_files/multiple_files/trivial/c.click"
      ).map(ClickToAbstractNetwork.buildConfig(_, prefixedElements = true)),
      InterClickLinksParser.parseLinks("src/main/resources/click_test_files/multiple_files/trivial/abc.links"),
      RuleSetBuilder.buildRuleSetFromFile("src/main/resources/spec_lang_tests/multiple_failing_cascading_simple")
    )

    var crtExecutor = ctx
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    ctx shouldBe a [ClickExecutionContext]
    ctx.instructions should have size 6
    ctx.links should have size 5
    ctx.checkInstructions should have size 3

    crtExecutor.stuckStates should have length 0
    crtExecutor.okStates should have length 0
    crtExecutor.failedStates should have length 1
  }

}
