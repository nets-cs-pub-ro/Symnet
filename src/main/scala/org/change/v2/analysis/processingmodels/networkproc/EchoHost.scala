package org.change.v2.analysis.processingmodels.networkproc

import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.processingmodels.instructions.{InstructionBlock, Assign}
import org.change.v2.analysis.processingmodels.{Instruction, State}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object EchoHost extends Instruction {

  override def apply(s: State, v: Boolean): (List[State], List[State]) =
    InstructionBlock(
      Swap("IP-Dst", "IP-Src"),
      Swap("Port-Dst", "Port-Src")
    )(s,v)

}

case class Swap(a: String, b: String) extends Instruction {

  override def apply(s: State, v: Boolean): (List[State], List[State]) =
    InstructionBlock(
      Assign("Temp", :@(a)),
      Assign(a, :@(b)),
      Assign(b, :@("Temp"))
    )(s,v)

}
