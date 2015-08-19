package org.change.v2.analysis.processingmodels

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
abstract class Instruction(id: String = "") extends (State => (List[State], List[State])) {

  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  def apply(s: State): (List[State], List[State]) = (List(s), Nil)

  def getIdHash = id.hashCode
}

