package org.change.v2.executor

import org.change.v2.analysis.processingmodels.State

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
trait ExecutionContext {

  def execute(verbose: Boolean = false): ExecutionContext

  def isDone: Boolean = okStates.isEmpty

  def okStates: List[State]
  def failedStates: List[State]
  def stuckStates: List[State]

}
