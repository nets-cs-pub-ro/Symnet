import org.change.v2.analysis.constraint.E
import org.change.v2.analysis.expression.concrete.nonprimitive.{:@, :+:}
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.memory.{Value, MemorySpace}
import org.change.v2.analysis.processingmodels.{InstructionBlock, State}
import org.change.v2.analysis.processingmodels.instructions._
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class InstructionTests extends FlatSpec with Matchers {

  "Rewrite" should "push another value on the assignment stack" in {

    val (s,f) = InstructionBlock(
      Assign("IP", SymbolicValue())

    )(State.bigBang)

    s.head.memory.eval("IP") shouldBe a [Some[_]]
  }

  "Dup" should "make two symbol refer the same value" in {

    val (s,f) = InstructionBlock(List(
      Assign("IP", ConstantValue(2)),
      Duplicate("IP-Clone", "IP"),
      Same("IP-Clone", "IP")
    ))(State.bigBang)

    val afterState = s.head

    afterState.memory.eval("IP-Clone") shouldBe a [Some[_]]
    afterState.memory.eval("IP").get.e.id shouldEqual afterState.memory.eval("IP-Clone").get.e.id
    afterState.memory.eval("IP").get.e should be theSameInstanceAs afterState.memory.eval("IP-Clone").get.e
  }

  "Constrain" should "correctly add another constraint to a symbol" in {
    val rwIP = Assign("IP", SymbolicValue())

    val m = MemorySpace.clean
    val stateZero = State(m)

    val (s1,f1) = rwIP(stateZero)

    // TODO
  }

  "If" should "branch execution correctly" in {
    val (s,f) = InstructionBlock(List(
      Assign("IP", ConstantValue(2)),
      If(Constrain("IP", :==:(ConstantValue(2))), NoOp, NoOp)
    ))(State.bigBang)

    s should have length (1)
    f should have length (1)
  }

  "Deferrable E" should "pass when applied to the same expression" in {
    val (s,f) = InstructionBlock(List(
      Assign("A", SymbolicValue()),
      Assign("B", SymbolicValue()),

      Assign("S1", :+:(:@("A"), :@("B"))),
      Assign("S2", :+:(:@("A"), :@("B"))),
      Constrain("S1", :==:(:@("S2")))
    ))(State.bigBang)

    s should have length (1)
    f should have length (0)
  }

}
