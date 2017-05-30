
package org.change.v2.runners.sefl

import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions._

/**
  * A small gift from radu to symnetic.
  */
object SEFLCodeAndLinks {

  val i: Map[String, Instruction] = Map(
    "a" -> NoOp,
    "tmp" -> Forward("b"),
    "b" -> Fail("bla"),
    "c" -> Fail("wut")
  )

  val l: Map[String, String] = Map(
    "a" -> "tmp",
    "b" -> "c"
  )
}
