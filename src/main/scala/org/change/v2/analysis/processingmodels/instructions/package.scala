package org.change.v2.analysis.processingmodels

import org.change.v2.analysis.memory.MemorySpace

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
package object instructions {

  def optionToStatePair(previousState: State, error: ErrorCause)(block: State => Option[MemorySpace]): (List[State], List[State]) = {
    val nm = block(previousState)
    nm.map(m => (List(State(m, previousState.history, None, previousState.instructionHistory)), Nil)).
      getOrElse((Nil, List(State(previousState.memory, previousState.history, Some(error), previousState.instructionHistory))))
  }

  def stateToError(previousState: State, error: ErrorCause) = optionToStatePair(previousState, error)(_ => None)
}
