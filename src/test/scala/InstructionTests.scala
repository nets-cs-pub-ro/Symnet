import org.change.v2.analysis.expression.concrete.nonprimitive.{:@, :+:}
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.memory.{Value, MemorySpace}
import org.change.v2.analysis.processingmodels.{State}
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.instructions.ForAll._
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
      Assign("IP-Clone", :@("IP")),
      Constrain("IP-Clone", :==:(:@("IP")))
    ))(State.bigBang)

    val afterState = s.head

    afterState.memory.eval("IP-Clone") shouldBe a [Some[_]]
    afterState.memory.eval("IP").get.e.id shouldEqual afterState.memory.eval("IP-Clone").get.e.id
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

  "If" should "branch execution correctly in case of symbolics" in {
    val (s,f) = InstructionBlock(
      Assign("IP", SymbolicValue()),
      If(Constrain("IP", :==:(ConstantValue(2))),
        InstructionBlock(
          Constrain("IP", :==:(ConstantValue(3)))
        ),
        InstructionBlock(
          Allocate("IP"),
          Assign("IP", SymbolicValue()),
          Constrain("IP", :==:(ConstantValue(2)))
        ))
    )(State.bigBang)

    s should have length (1)
    f should have length (1)
  }

  "Deferrable E" should "pass when applied to the same expression" in {
    val (s,f) = InstructionBlock(List(
      Assign("A", SymbolicValue()),
      Assign("B", SymbolicValue()),

      Assign("S1", :+:(:@("A"), :@("B"))),
      Assign("S2", :+:(:@("A"), :@("B"))),
      Constrain("S1", :~:(:==:(:@("S2"))))
    ))(State.bigBang)

    s should have length (0)
    f should have length (1)
  }

  "ForAll" should "iterate through all matches and properly set the bindings" in {
    val (s,f) = InstructionBlock(
      Assign("A-1", SymbolicValue()),
      Assign("B-1", :@("A-1")),
      Assign("Init", :@("A-1")),

      ForAll("x" :<- ".*-1") (
        Deallocate("x")
      )

    )(State.bigBang)

//    s should have length (1)
//    f should have length (0)
//    s.head.memory.symbols should not contain("B-1")
//    s.head.memory.symbols should not contain("A-1")
  }

}
