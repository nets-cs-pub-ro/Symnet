package org.change.v2.analysis.processingmodels.networkproc

import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.expression.concrete.nonprimitive.Plus
import org.change.v2.analysis.processingmodels.instructions.{ContextualRewrite, Rewrite, Dup, :+:}
import org.change.v2.analysis.processingmodels.{InstructionBlock, State, Instruction}

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
        Dup("Old-SEQ", "SEQ"),
        Rewrite("Delta", ConstantValue(l)),
        ContextualRewrite("New-SEQ",  :+:("SEQ","Delta")),
        Dup("SEQ", "New-SEQ")
      )(s)
    case _ =>
      InstructionBlock(
        Dup("Old-SEQ", "SEQ"),
        Rewrite("Delta", SymbolicValue()),
        ContextualRewrite("New-SEQ",  :+:("SEQ","Delta")),
        Dup("SEQ", "New-SEQ")
      )(s)
  }
}
