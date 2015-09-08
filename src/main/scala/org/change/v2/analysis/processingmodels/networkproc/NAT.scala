package org.change.v2.analysis.processingmodels.networkproc

import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.expression.concrete.nonprimitive.Symbol
import org.change.v2.analysis.processingmodels.{Instruction, State}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class NATToInternet(toOutsideIP: Long) extends Instruction {

  override def apply(s: State, v: Boolean): (List[State], List[State]) =
    InstructionBlock(
      AssignNamedSymbol("Old-IP-Src", Symbol("IP-Src")),
      AssignNamedSymbol("Old-Port-Src", Symbol("Port-Src")),

      AssignNamedSymbol("New-IP-Src", ConstantValue(toOutsideIP)),
      AssignNamedSymbol("New-Port-Src", SymbolicValue()),

      AssignNamedSymbol("IP-Src", Symbol("New-IP-Src")),
      AssignNamedSymbol("Port-Src", Symbol("New-Port-Src"))
    )(s,v)
}

object NATFromInternet extends Instruction {

  override def apply(s: State, v: Boolean): (List[State], List[State]) =
    InstructionBlock(
      If(ConstrainNamedSymbol("IP-Dst", :==:(Symbol("New-IP-Src"))),
        If(ConstrainNamedSymbol("Port-Dst", :==:(Symbol("New-Port-Src"))),
          InstructionBlock(
            AssignNamedSymbol("IP-Dst", Symbol("Old-IP-Src")),
            AssignNamedSymbol("Port-Dst", Symbol("Old-Port-Src"))
          ),
          NoOp
        ),
        NoOp
      )
    )(s,v)
}

