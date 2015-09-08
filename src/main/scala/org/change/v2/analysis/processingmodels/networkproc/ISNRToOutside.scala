package org.change.v2.analysis.processingmodels.networkproc

import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.expression.concrete.nonprimitive.{:+:, Symbol}
import org.change.v2.analysis.processingmodels.instructions.{InstructionBlock, AssignNamedSymbol}
import org.change.v2.analysis.processingmodels.{State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class ISNRToOutside(delta: Option[Long]) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) = delta match {
    case Some(l) =>
      InstructionBlock(
        AssignNamedSymbol("Old-SEQ", Symbol("SEQ")),
        AssignNamedSymbol("Delta", ConstantValue(l)),
        AssignNamedSymbol("SEQ",  :+:(Symbol("SEQ"),Symbol("Delta")))
      )(s,v)
    case _ =>
      InstructionBlock(
        AssignNamedSymbol("Old-SEQ", Symbol("SEQ")),
        AssignNamedSymbol("Delta", SymbolicValue()),
        AssignNamedSymbol("SEQ",  :+:(Symbol("SEQ"),Symbol("Delta")))
      )(s,v)
  }
}
