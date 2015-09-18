package clickfiletoexecutor

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.executor.clickabstractnetwork._
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class ClickToExecutorTests extends FlatSpec with Matchers {

  "A src-dst click" should "generate a valid no op executor" in {
    val absNet = ClickToAbstractNetwork.buildConfig("src/main/resources/click_test_files/SrcDst.click")
    val executor = ClickExecutionContext.fromSingle(absNet)

    executor shouldBe a [ClickExecutionContext]
  }

  "A src-dst click executor" should "propagate the bing-bang state to dst, becoming stuck" in {
    val absNet = ClickToAbstractNetwork.buildConfig("src/main/resources/click_test_files/SrcDst.click")
    val executor = ClickExecutionContext.fromSingle(absNet)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    crtExecutor.stuckStates should have length (1)
    crtExecutor.stuckStates.head.history should have length (4)
  }

  "A src-paint-dst executor" should "correctly paint the bloody flow" in {
    val absNet = ClickToAbstractNetwork.buildConfig("src/main/resources/click_test_files/SrcPaintDst.click")
    val executor = ClickExecutionContext.fromSingle(absNet)

    var crtExecutor = executor
    while(! crtExecutor.isDone) {
      crtExecutor = crtExecutor.execute()
    }

    crtExecutor.stuckStates should have length (1)
    crtExecutor.stuckStates.head.history should have length (6)
    crtExecutor.stuckStates.head.memory.eval("COLOR").get.e should be (ConstantValue(10))
  }

  "A src-classif-dst click" should "generate a valid executor" in {
    val absNet = ClickToAbstractNetwork.buildConfig("src/main/resources/click_test_files/Classif.click")
    val executor = ClickExecutionContext.fromSingle(absNet)

    executor shouldBe a [ClickExecutionContext]
  }

}
