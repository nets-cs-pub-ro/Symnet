import org.change.v2.analysis.constraint.E
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.memory.{Value, MemorySpace}
import org.change.v2.analysis.processingmodels.{InstructionBlock, State}
import org.change.v2.analysis.processingmodels.instructions.{Dup, Constrain, Rewrite}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class Z3Validity extends FlatSpec with Matchers {

  "A clean MemorySpace" should "be Z3-valid" in {
    MemorySpace.clean.isZ3Valid should be (true)

    val (s1,f1) = Rewrite("IP", ConstantValue(2))(State(MemorySpace.clean))
    s1.head.memory.isZ3Valid should be (true)
  }

  "A constant" should "not be equal to another" in {
    val m = MemorySpace.clean
    val stateZero = State(m)

    val (s1,f1) = Rewrite("IP", ConstantValue(2))(stateZero)
    val (s2, f2) = Constrain("IP", E(3))(s1.head)

    s2.head.memory.isZ3Valid should be (false)

    val (s3,f3) = Rewrite("IP", ConstantValue(5))(s2.head)
    s3.head.memory.isZ3Valid should be (true)

    val (s4, f4) = Constrain("IP", E(5))(s3.head)
    s4.head.memory.isZ3Valid should be (true)
  }

  "Dup" should "make symbols equal" in {
    val (s,f) = InstructionBlock(List(
      Rewrite("IP", SymbolicValue()),
      Dup("IP-Clone", "IP"),

      Constrain("IP", E(2)),
      Constrain("IP-Clone", E(3))
    ))(State.bigBang)

    val afterState = s.head

    afterState.memory.isZ3Valid should be (false)
  }

}
