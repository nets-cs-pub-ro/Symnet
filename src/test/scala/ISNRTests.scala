import org.change.v2.analysis.expression.concrete.SymbolicValue
import org.change.v2.analysis.processingmodels.instructions.{Rewrite, Same}
import org.scalatest.{Matchers, FlatSpec}
import org.change.v2.analysis.processingmodels.networkproc._
import org.change.v2.analysis.processingmodels.{InstructionBlock, State}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class ISNRTests extends FlatSpec with Matchers {

  "ISNRToOutside" should "rewrite SEQ" in {
    val (s,f) = InstructionBlock(
      Rewrite("SEQ", SymbolicValue()),
      ISNRToOutside(None),
      Same("SEQ", "New-SEQ")
    )(State.bigBang)

    s should have length (1)
    f should have length (0)
  }

  "ISNR to outside and back" should "preserve SEQ value" in {
    val (s,f) = InstructionBlock(
      Rewrite("SEQ", SymbolicValue()),
      ISNRToOutside(None),
      ISNRToInside
    )(State.bigBang)

    s should have length (1)
    f should have length (0)
  }

  "NAT to outside and back" should "preserve initial values" in {
    val (s,f) = InstructionBlock(
      Rewrite("IP-Src", SymbolicValue()),
      Rewrite("IP-Dst", SymbolicValue()),
      Rewrite("Port-Src", SymbolicValue()),
      Rewrite("Port-Dst", SymbolicValue()),
      NATToInternet(1),
      EchoHost,
      NATFromInternet,
      Same("IP-Dst", "Old-IP-Src")
    )(State.bigBang)

    s should have length (1)
    f should have length (0)
  }

}
