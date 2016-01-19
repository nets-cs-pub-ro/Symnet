package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.memory.State
import org.change.v2.analysis.processingmodels.Instruction

/**
 * A small gift from radu to symnetic.
 */
case class Fork(forkBlocks: Seq[Instruction]) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, verbose: Boolean): (List[State], List[State]) = {
    val (s, f) = forkBlocks.map(i => i(s)).unzip
    (s.flatten.toList, f.flatten.toList)
  }
}
