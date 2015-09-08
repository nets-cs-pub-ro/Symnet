import org.change.v2.analysis.constraint.GT
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.expression.concrete.nonprimitive.{Plus, Symbol}
import org.change.v2.analysis.processingmodels.instructions._
import org.scalatest.{Matchers, FlatSpec}
import org.change.v2.analysis.processingmodels.networkproc._
import org.change.v2.analysis.processingmodels.State

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class BasicProcessingTests extends FlatSpec with Matchers {

  "ISNRToOutside" should "rewrite SEQ" in {
    val (s,f) = InstructionBlock(
      AssignNamedSymbol("SEQ", SymbolicValue()),
      ISNRToOutside(Some(5)),
      ConstrainNamedSymbol("SEQ", :==:(Symbol("Old-SEQ")))
    )(State.bigBang)

    s should have length (0)
    f should have length (1)
  }

  "ISNR to outside and back" should "preserve SEQ value" in {
    val (s,f) = InstructionBlock(
      AssignNamedSymbol("SEQ", SymbolicValue()),
      ISNRToOutside(None),
      ConstrainNamedSymbol("Delta", :>:(ConstantValue(0))),
      ISNRToInside,
      ConstrainNamedSymbol("SEQ", :==:(Symbol("Old-SEQ")))
    )(State.bigBang)

    s should have length (1)
    f should have length (0)
  }

  "NAT to outside and back" should "preserve initial values" in {
    val (s,f) = InstructionBlock(
      AssignNamedSymbol("IP-Src", SymbolicValue()),
      AssignNamedSymbol("IP-Dst", SymbolicValue()),
      AssignNamedSymbol("Port-Src", SymbolicValue()),
      AssignNamedSymbol("Port-Dst", SymbolicValue()),

      NATToInternet(1),

      EchoHost,

      NATFromInternet,
      ConstrainNamedSymbol("IP-Dst", :==:(Symbol("Old-IP-Src")))
    )(State.bigBang)

    s should have length (1)
    f should have length (2)
  }

}
