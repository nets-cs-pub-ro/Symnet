package org.change.v2.analysis.processingmodels.networkproc

import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.processingmodels.instructions.{Rewrite, Dup}
import org.change.v2.analysis.processingmodels.{Instruction, InstructionBlock, State}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object EchoHost extends Instruction {

  override def apply(s: State): (List[State], List[State]) =
    InstructionBlock(
      Swap("IP-Dst", "IP-Src"),
      Swap("Port-Dst", "Port-Src")
    )(s)

}

case class Swap(a: String, b: String) extends Instruction {

  override def apply(s: State): (List[State], List[State]) =
    InstructionBlock(
      Dup("Temp", a),
      Dup(a, b),
      Dup(b, "Temp")
    )(s)

}
