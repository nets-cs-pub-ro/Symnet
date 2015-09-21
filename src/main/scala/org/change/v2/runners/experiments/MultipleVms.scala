package org.change.v2.runners.experiments

import java.io.{FilenameFilter, File}

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.parser.interclicklinks.InterClickLinksParser
import org.change.parser.startpoints.StartPointParser
import org.change.symbolicexec.verification.RuleSetBuilder
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.change.parser.startpoints.StartPointParser

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
      verificationConditions = Nil,
      //,RuleSetBuilder.buildRuleSetFromFile("src/main/resources/click_test_files/multiple_files/mul_vm_playground/rules.rules"),
      startElems = Some(StartPointParser.parseStarts(
        "src/main/resources/click_test_files/multiple_files/mul_vm_playground/start.start"
      ))
    )

    var crtExecutor = ctx
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    println(crtExecutor.stringifyStates())
  }

}
