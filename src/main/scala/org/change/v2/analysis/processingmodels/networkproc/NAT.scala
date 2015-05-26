package org.change.v2.analysis.processingmodels.networkproc

import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.{InstructionBlock, Instruction, State}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class NATToInternet(toOutsideIP: Long) extends Instruction {

  override def apply(s: State): (List[State], List[State]) =
    InstructionBlock(
      Dup("Old-IP-Src", "IP-Src"),
      Dup("Old-Port-Src", "Port-Src"),

      Rewrite("New-IP-Src", ConstantValue(toOutsideIP)),
      Rewrite("New-Port-Src", SymbolicValue()),

      Dup("IP-Src", "New-IP-Src"),
      Dup("Port-Src", "New-Port-Src")
    )(s)
}

object NATFromInternet extends Instruction {

  override def apply(s: State): (List[State], List[State]) =
    InstructionBlock(
      If(Same("IP-Dst", "New-IP-Src"),
        If(Same("Port-Dst", "New-Port-Src"),
          InstructionBlock(
            Dup("IP-Dst", "Old-IP-Src"),
            Dup("Port-Dst", "Old-Port-Src")
          ),
          NoOp
        ),
        NoOp
      )
    )(s)
}

