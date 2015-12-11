package org.change.v2.analysis.processingmodels.networkproc

import org.change.v2.analysis.expression.concrete.nonprimitive.{:-:, Symbol}
import org.change.v2.analysis.memory.State
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.analysis.processingmodels.Instruction
/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object ISNRToInside extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) =
    InstructionBlock(
      AssignNamedSymbol("SEQ", :-:(Symbol("SEQ"), Symbol("Delta")))
    )(s,v)
}
