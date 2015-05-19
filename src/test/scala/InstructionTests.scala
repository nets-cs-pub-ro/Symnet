import org.change.v2.analysis.constraint.E
import org.change.v2.analysis.expression.concrete.SymbolicValue
import org.change.v2.analysis.memory.{Value, MemorySpace}
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.processingmodels.instructions.{Rewrite, Constrain}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class InstructionTests extends FlatSpec with Matchers {

  "Rewrite" should "push another value on the assignment stack" in {
    val rwIP = Rewrite("IP", SymbolicValue())

    val m = MemorySpace.clean
    val stateZero = State(m)

    val (s,f) = rwIP(stateZero)

    val nextVer = s.head.memory.symbolSSAVersion("IP")

    nextVer should be (1)
    s.head.memory.GET("IP") shouldBe a [Some[Value]]
  }

  "Constrain" should "correctly add another constraint to a symbol" in {
    val rwIP = Rewrite("IP", SymbolicValue())

    val m = MemorySpace.clean
    val stateZero = State(m)

    val (s1,f1) = rwIP(stateZero)

    val nextVer = s1.head.memory.symbolSSAVersion("IP")

    nextVer should be (1)
    s1.head.memory.GET("IP") shouldBe a [Some[Value]]

    val (s2, f2) = Constrain("IP", E(2))(s1.head)
    s2.head.memory.GET("IP").get.cts should have size (1)
    s1.head.memory.symbolSSAVersion("IP") should be (1)
  }

}
