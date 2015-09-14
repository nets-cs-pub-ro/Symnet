package modelexample

import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.processingmodels.instructions._
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class TestModelExample extends FlatSpec with Matchers {

  "A valid model" should "be able to produce concrete examples" in {
    val s = State.bigBang

    val finalState = InstructionBlock (
      Allocate("a"),
      Assign("a", SymbolicValue()),
      Assign("b", SymbolicValue()),
      Constrain("b", :==:(ConstantValue(0))),
      If(Constrain("a", :<:(ConstantValue(10))),
        NoOp
      )
    )(s)

    val a = finalState._1.apply(0)
    val b = finalState._1.apply(1)

    val ea = a.memory.exampleFor("a").get
    val eb = b.memory.exampleFor("a").get

    assert(ea < 10)
    assert(eb >= 10)

    b.memory.exampleFor("b").get should be (0)
  }

}
