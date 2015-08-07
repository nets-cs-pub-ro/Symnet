package org.change.v2.runners

import com.googlecode.scalascriptengine.ScalaScriptEngine
import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{InstructionBlock, State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object RunSample {

  def processor = new Instruction {

    override def apply(s: State): (List[State], List[State]) =
      InstructionBlock(
        Allocate("IP"),
        Assign("IP", ConstantValue(3)),
        Constrain("IP", :<:(ConstantValue(10))),
        Constrain("IP", :==:(:@("IP"))),
        Duplicate("IP2", "IP"),
        Same("IP2", "IP"),
        Duplicate("IP3", "IP"),
        Same("IP3", "IP2"),
        Assign("IP", ConstantValue(3)),
        Same("IP3", "IP")
      )(s)
  }

  def main(args: Array[String]): Unit = {
    println(interpret)
  }

  def interpret: String = {
    val (s, f) = processor(State.bigBang)

    (s"Successful\n\n$s\n") +
    (s"Bad\n\n$f")
  }

}
