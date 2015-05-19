import org.change.v2.analysis.memory.MemorySpace
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class Z3Validity extends FlatSpec with Matchers {

  "A clean MemorySpace" should "be Z3-valid" in {
    MemorySpace.clean.isZ3Valid should be (true)
  }

}
