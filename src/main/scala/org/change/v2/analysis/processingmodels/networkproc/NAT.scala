package org.change.v2.analysis.processingmodels.networkproc

import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.processingmodels.{InstructionBlock, Instruction, State}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class NATToInternet(toOutsideIP: Long) extends Instruction {

  override def apply(s: State): (List[State], List[State]) =
    InstructionBlock(
      Assign("Old-IP-Src", :@("IP-Src")),
      Assign("Old-Port-Src", :@("Port-Src")),

      Assign("New-IP-Src", ConstantValue(toOutsideIP)),
      Assign("New-Port-Src", SymbolicValue()),

      Assign("IP-Src", :@("New-IP-Src")),
      Assign("Port-Src", :@("New-Port-Src"))
    )(s)
}

object NATFromInternet extends Instruction {

  override def apply(s: State): (List[State], List[State]) =
    InstructionBlock(
      If(Constrain("IP-Dst", :==:(:@("New-IP-Src"))),
        If(Constrain("Port-Dst", :==:(:@("New-Port-Src"))),
          InstructionBlock(
            Assign("IP-Dst", :@("Old-IP-Src")),
            Assign("Port-Dst", :@("Old-Port-Src"))
          ),
          NoOp
        ),
        NoOp
      )
    )(s)
}

