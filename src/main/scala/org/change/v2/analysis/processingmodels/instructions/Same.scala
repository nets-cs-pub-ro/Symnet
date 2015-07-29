package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.processingmodels.{State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Same(a: String, b: String) extends Instruction {
  override def apply(s: State): (List[State], List[State]) = {
    optionToStatePair(s, "Symbols referring different values") {
      _.memory.Same(a,b)
    }
  }
}
