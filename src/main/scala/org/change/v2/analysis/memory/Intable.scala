package org.change.v2.analysis.memory

import org.change.v2.analysis.memory.State

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
trait Intable {
  def apply(s: State): Option[Int]
}