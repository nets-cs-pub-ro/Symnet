package org.change.v2.analysis.processingmodels.networkproc

import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.expression.concrete.nonprimitive.{:+:, :@}
import org.change.v2.analysis.processingmodels.instructions.{InstructionBlock, Assign}
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
  override def apply(s: State): (List[State], List[State]) = delta match {
    case Some(l) =>
      InstructionBlock(
        Assign("Old-SEQ", :@("SEQ")),
        Assign("Delta", ConstantValue(l)),
        Assign("SEQ",  :+:(:@("SEQ"),:@("Delta")))
      )(s)
    case _ =>
      InstructionBlock(
        Assign("Old-SEQ", :@("SEQ")),
        Assign("Delta", SymbolicValue()),
        Assign("SEQ",  :+:(:@("SEQ"),:@("Delta")))
      )(s)
  }
}
