package org.change.v2.runners.experiments

import java.io.{File, FileOutputStream, PrintWriter}

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.symbolicexec.verification.RuleSetBuilder
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object RuleSetVerification {

  def main(args: Array[String]) {
    val clickConfig = "src/main/resources/click_test_files/SrcDst.click"
    val absNet = ClickToAbstractNetwork.buildConfig(clickConfig)
    val rules = RuleSetBuilder.buildRuleSetFromFile("src/main/resources/spec_lang_tests/simple")
    val executor = ClickExecutionContext.fromSingle(absNet, rules)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute(true)
    }

    val pr = new PrintWriter(new FileOutputStream(new File("withrules.output")))

    pr.print(crtExecutor.stringifyStates(includeOk = false, includeFailed = true))
    pr.close()
  }

}
