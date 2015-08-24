package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.constraint.NOT
import org.change.v2.analysis.processingmodels.{State, Instruction}

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
    // This is quite inappropriate
    case Fail(_) => elseWhat(s)
    case i @ Constrain(what, withWhat, _) => {
      (withWhat instantiate s) match {
        case Left(c) if s.memory.symbolIsAssigned(what) => {
          val (sa, fa) = InstructionBlock(Constrain(what, withWhat, Some(c)), thenWhat)(s)
          val (sb, fb) = InstructionBlock(Constrain(what, :~:(withWhat), Some(c)), elseWhat)(s)
          (sa ++ sb, fa ++ fb)
        }

        case _ => elseWhat(s)
      }
    }
    case _ => stateToError(s, "Bad test instruction")
  }
}
