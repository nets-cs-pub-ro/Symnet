package org.change.v2.runners.experiments

import java.io.{File, FileOutputStream, FilenameFilter, PrintStream}

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.parser.interclicklinks.InterClickLinksParser
import org.change.parser.startpoints.StartPointParser
import org.change.symbolicexec.verification.RuleSetBuilder
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object MultipleVmsFacultatea {

  def main(args: Array[String]) = {
    val clicksFolder = new File("src/main/resources/facultatea")

    val clicks = clicksFolder.list(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = name.endsWith(".click")
    }).sorted.map(clicksFolder.getPath + File.separatorChar + _)

    val ctx = ClickExecutionContext.buildAggregated(
      clicks.map(ClickToAbstractNetwork.buildConfig(_, prefixedElements = true)),
      Nil,//InterClickLinksParser.parseLinks("src/main/resources/click_test_files/facultatea/links.links"),
      verificationConditions = Nil, //RuleSetBuilder.buildRuleSetFromFile("src/main/resources/click_test_files/facultatea/rules.rules"),
      startElems = Some(StartPointParser.parseStarts(
        "src/main/resources/facultatea/start.start"
      ))
    )

    var crtExecutor = ctx
    var steps = 0
    while(! crtExecutor.isDone && steps < 100) {
      steps += 1
      crtExecutor = crtExecutor.execute(verbose=true)
    }

    val output = new PrintStream(new FileOutputStream(new File("facultatea.output")))
    output.println(crtExecutor.stringifyStates())
    output.close()
    println("Done")
  }

}
