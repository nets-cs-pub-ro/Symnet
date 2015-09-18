package clickfiletoexecutor.rule

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.symbolicexec.verification.RuleSetBuilder
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.scalatest.{FlatSpec, Matchers}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class RuleVerificationTests extends FlatSpec with Matchers{

  "A file with rules" should "be parsable and result in an executor" in {
    val absNet = ClickToAbstractNetwork.buildConfig("src/main/resources/click_test_files/SrcDst.click")
    val rules = RuleSetBuilder.buildRuleSetFromFile("src/main/resources/spec_lang_tests/simple")
    val executor = ClickExecutionContext.fromSingle(absNet, rules)
    executor shouldBe a [ClickExecutionContext]
  }

  "Rules" should "should add constraints to ports" in {
    val absNet = ClickToAbstractNetwork.buildConfig("src/main/resources/click_test_files/SrcDst.click")
    val rules = RuleSetBuilder.buildRuleSetFromFile("src/main/resources/spec_lang_tests/simple")
    val executor = ClickExecutionContext.fromSingle(absNet, rules)

    executor.checkInstructions.keys should contain ("source-out")
  }

}
