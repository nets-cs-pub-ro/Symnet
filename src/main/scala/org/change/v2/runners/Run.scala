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
object Run {

  def processor = new Instruction {

    override def apply(s: State): (List[State], List[State]) =
      InstructionBlock(

InstructionBlock(      Assign("SRC", SymbolicValue()),      Assign("DST", SymbolicValue()),      Assign("PROTO", ConstantValue(6)),      Allocate("SRC"),      Allocate("DST"),      Allocate("PROTO"),      Assign("SRC",ConstantValue(100)),      Assign("DST",ConstantValue(200)),      Assign("PROTO",ConstantValue(4)),))(s)
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
