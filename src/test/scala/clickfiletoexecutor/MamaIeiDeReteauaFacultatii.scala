package clickfiletoexecutor

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class MamaIeiDeReteauaFacultatii extends FlatSpec with  Matchers {

  val clickFilePath = "src/main/resources/click_test_files/ASA-click.click"

  "The ASA" should "be parsable" in {
    val absNet = ClickToAbstractNetwork.buildConfig(clickFilePath)
    val executor = ClickExecutionContext.fromSingle(absNet)

    executor shouldBe a [ClickExecutionContext]
  }

  "Facultatea" should "work" in {
    val absNet = ClickToAbstractNetwork.buildConfig(clickFilePath)
    val executor = ClickExecutionContext.fromSingle(absNet)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    crtExecutor.stuckStates
  }
}
