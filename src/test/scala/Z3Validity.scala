import org.change.v2.analysis.constraint.E
import org.change.v2.analysis.expression.concrete.nonprimitive.Symbol
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.memory.{Value, MemorySpace}
import org.change.v2.analysis.processingmodels.{State}
import org.change.v2.analysis.processingmodels.instructions.{InstructionBlock, :==:, ConstrainNamedSymbol, AssignNamedSymbol}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class Z3Validity extends FlatSpec with Matchers {

  "A clean MemorySpace" should "be Z3-valid" in {
    MemorySpace.clean.isZ3Valid should be (true)

    val (s1,f1) = AssignNamedSymbol("IP", ConstantValue(2))(State(MemorySpace.clean))
    s1.head.memory.isZ3Valid should be (true)
  }

  "A constant" should "not be equal to another" in {
    val m = MemorySpace.clean
    val stateZero = State(m)

    val (s1,f1) = AssignNamedSymbol("IP", ConstantValue(2))(stateZero)
    val (s2, f2) = ConstrainNamedSymbol("IP", :==:(ConstantValue(3)))(s1.head)

    s2 should have length (0)
    f2 should have length (1)
  }

  "Dup" should "make symbols equal" in {
    val (s,f) = InstructionBlock(List(
      AssignNamedSymbol("IP", SymbolicValue()),
      AssignNamedSymbol("IP-Clone", Symbol("IP")),

      ConstrainNamedSymbol("IP", :==:(ConstantValue(2))),
      ConstrainNamedSymbol("IP-Clone", :==:(ConstantValue(3)))
    ))(State.bigBang)

    s should have length (0)
    f should have length (1)
  }

}
