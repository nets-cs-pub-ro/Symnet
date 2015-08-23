package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.constraint.NOT
import org.change.v2.analysis.processingmodels.{InstructionBlock, State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class If(testInstr: Instruction, thenWhat: Instruction, elseWhat:Instruction = NoOp) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State): (List[State], List[State]) = testInstr match {
    case i @ Constrain(what, withWhat) => {
      val (sa, fa) = InstructionBlock(i, thenWhat)(s)
      val (sb, fb) = InstructionBlock(Constrain(what, :~:(withWhat)), elseWhat)(s)
      (sa ++ sb, fa ++ fb)
    }
    case _ => stateToError(s, "Bad test instruction")
  }
}
