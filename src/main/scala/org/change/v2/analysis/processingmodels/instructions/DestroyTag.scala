package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.memory.{TagExp, Intable}
import org.change.v2.analysis.processingmodels.{State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class DestroyTag(name: String) extends Instruction {
  override def apply(s: State, v: Boolean): (List[State], List[State]) = {
    optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Error during untagging of $name") (s => {
        s.memory.UnTag(name)
      })
  }
}
