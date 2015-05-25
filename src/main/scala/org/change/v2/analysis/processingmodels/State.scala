package org.change.v2.analysis.processingmodels

import org.change.v2.analysis.memory.MemorySpace

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class State(memory: MemorySpace = MemorySpace.clean,
                 history: List[LocationId] = Nil,
                 errorCause: Option[ErrorCause] = None)

object State {
 def bigBang: State = State(MemorySpace.clean)
}
