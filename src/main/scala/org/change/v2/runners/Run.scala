package org.change.v2.runners

import com.googlecode.scalascriptengine.ScalaScriptEngine
import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.processingmodels.instructions.{NoOp, Assign, Duplicate}
import org.change.v2.analysis.processingmodels.{InstructionBlock, State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object Run {

  def processor = new Instruction {

    override def apply(s: State): (List[State], List[State]) =
      InstructionBlock(
        NoOp
      )(s)
  }

  def main(args: Array[String]): Unit = {
    val (s, f) = processor(State.bigBang)

    println(s"Successful\n\n$s")
    println(s"Bad\n\n$f")
  }

}
