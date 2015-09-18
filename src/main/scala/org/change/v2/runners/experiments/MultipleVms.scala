package org.change.v2.runners.experiments

import java.io.{FilenameFilter, File}

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.parser.interclicklinks.InterClickLinksParser
import org.change.symbolicexec.verification.RuleSetBuilder
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object MultipleVms {

  def main(args: Array[String]) = {
    val clicksFolder = new File("src/main/resources/click_test_files/multiple_files/mul_vm_playground")

    val clicks = clicksFolder.list(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = name.endsWith(".click")
    }).sorted.map(clicksFolder.getPath + File.separatorChar + _)

    val ctx = ClickExecutionContext.buildAggregated(
      clicks.map(ClickToAbstractNetwork.buildConfig(_, prefixedElements = true)),
      InterClickLinksParser.parseLinks("src/main/resources/click_test_files/multiple_files/mul_vm_playground/links.links"),
      RuleSetBuilder.buildRuleSetFromFile("src/main/resources/click_test_files/multiple_files/mul_vm_playground/rules.rules")
    )

    var crtExecutor = ctx
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    println(crtExecutor.stringifyStates())
  }

}
