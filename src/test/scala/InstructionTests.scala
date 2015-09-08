import org.change.v2.analysis.expression.concrete.nonprimitive.{Symbol, :+:}
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.memory.{Value, MemorySpace}
import org.change.v2.analysis.processingmodels.{State}
import org.change.v2.analysis.processingmodels.instructions._
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class InstructionTests extends FlatSpec with Matchers {

  "Rewrite" should "push another value on the assignment stack" in {

    val (s,f) = InstructionBlock(
      AssignNamedSymbol("IP", SymbolicValue())

    )(State.bigBang)

    s.head.memory.eval("IP") shouldBe a [Some[_]]
  }

  "Dup" should "make two symbol refer the same value" in {

    val (s,f) = InstructionBlock(List(
      AssignNamedSymbol("IP", ConstantValue(2)),
      AssignNamedSymbol("IP-Clone", Symbol("IP")),
      ConstrainNamedSymbol("IP-Clone", :==:(Symbol("IP")))
    ))(State.bigBang)

    val afterState = s.head

    afterState.memory.eval("IP-Clone") shouldBe a [Some[_]]
    afterState.memory.eval("IP").get.e.id shouldEqual afterState.memory.eval("IP-Clone").get.e.id
  }

  "Constrain" should "correctly add another constraint to a symbol" in {
    val rwIP = AssignNamedSymbol("IP", SymbolicValue())

    val m = MemorySpace.clean
    val stateZero = State(m)

    val (s1,f1) = rwIP(stateZero)

    // TODO
  }

  "If" should "branch execution correctly" in {
    val (s,f) = InstructionBlock(List(
      AssignNamedSymbol("IP", ConstantValue(2)),
      If(ConstrainNamedSymbol("IP", :==:(ConstantValue(2))), NoOp, NoOp)
    ))(State.bigBang)

    s should have length (1)
    f should have length (1)
  }

  "If" should "branch execution correctly in case of symbolics" in {
    val (s,f) = InstructionBlock(
      AssignNamedSymbol("IP", SymbolicValue()),
      If(ConstrainNamedSymbol("IP", :==:(ConstantValue(2))),
        InstructionBlock(
          ConstrainNamedSymbol("IP", :==:(ConstantValue(3)))
        ),
        InstructionBlock(
          AllocateSymbol("IP"),
          AssignNamedSymbol("IP", SymbolicValue()),
          ConstrainNamedSymbol("IP", :==:(ConstantValue(2)))
        ))
    )(State.bigBang)

    s should have length (1)
    f should have length (1)
  }

  "Deferrable E" should "pass when applied to the same expression" in {
    val (s,f) = InstructionBlock(List(
      AssignNamedSymbol("A", SymbolicValue()),
      AssignNamedSymbol("B", SymbolicValue()),

      AssignNamedSymbol("S1", :+:(Symbol("A"), Symbol("B"))),
      AssignNamedSymbol("S2", :+:(Symbol("A"), Symbol("B"))),
      ConstrainNamedSymbol("S1", :~:(:==:(Symbol("S2"))))
    ))(State.bigBang)

    s should have length (0)
    f should have length (1)
  }

}
