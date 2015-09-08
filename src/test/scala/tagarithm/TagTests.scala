package tagarithm

import org.scalatest.{FlatSpec, Matchers}
import org.change.v2.analysis.expression.concrete.nonprimitive.{:@, :+:}
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.memory.{Tag, Value, MemorySpace}
import org.change.v2.analysis.processingmodels.{State}
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.instructions.ForAll._
import org.scalatest.{Matchers, FlatSpec}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class TagTests extends FlatSpec with Matchers{

  "Tag arithmetic" should "work" in {
    val s = State.bigBang

    val m = s.memory.Tag("L3", 10).Tag("L4", 100)

    val sPrime = State(m)

    (Tag("L4") + 10 + Tag("L3") - 5)(sPrime) should be (Some(115))
  }

}
