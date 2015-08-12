package org.change.v2.executor.clickabstractnetwork

import org.change.v2.analysis.processingmodels.{Instruction, State}
import org.change.v2.executor.ExecutionContext

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class ClickExecutionContext(
                           val instructions: Map[Int, (Instruction, String)]
                             ) extends ExecutionContext {
  override def execute: (List[State], List[State]) = (Nil, Nil)
}
