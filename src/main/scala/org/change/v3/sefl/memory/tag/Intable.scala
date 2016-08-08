package org.change.v3.sefl.memory.tag

import org.change.v3.sefl.memory.State

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
trait Intable {
  def apply(s: State): Option[Int]
}