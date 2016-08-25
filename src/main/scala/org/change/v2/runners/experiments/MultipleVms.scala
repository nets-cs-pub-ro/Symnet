package org.change.v2.runners.experiments

import java.io.{FileOutputStream, PrintStream, FilenameFilter, File}

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
    val clicksFolder = new File("src/main/resources/click_test_files/multiple_files/trivial")

    val clicks = clicksFolder.list(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = name.endsWith(".click")
    }).sorted.map(clicksFolder.getPath + File.separatorChar + _)

    val startOfBuild = System.currentTimeMillis()

    val ctx = ClickExecutionContext.buildAggregated(
      clicks.map(ClickToAbstractNetwork.buildConfig(_, prefixedElements = true)),
      InterClickLinksParser.parseLinks("src/main/resources/click_test_files/multiple_files/trivial/inter-click-links.links"),
      startElems = Some(StartPointParser.parseStarts(
        "src/main/resources/click_test_files/multiple_files/trivial/start.start"
      ))
    )

    var crtExecutor = ctx
    var steps = 0
    while(! crtExecutor.isDone && steps < 100) {
      steps += 1
      crtExecutor = crtExecutor.execute(verbose=true)
    }

    val successful = crtExecutor.stuckStates
    val  failed = crtExecutor.failedStates

    val outputOk = new PrintStream(new FileOutputStream(new File("sefl.ok.json")))

    outputOk.println(
      successful.map(_.jsonString).mkString("[", ",\n","]")
    )

    outputOk.close()

    val outputFail = new PrintStream(new FileOutputStream(new File("sefl.fail.json")))

    outputFail.println(
        failed.map(_.jsonString).mkString("[", ",\n", "]")
    )

    outputFail.close()

    println(s"Done: ${successful.length} ok, ${failed.length} failed")
  }

}
