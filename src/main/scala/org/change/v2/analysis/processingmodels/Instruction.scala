package org.change.v2.analysis.processingmodels

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
abstract class Instruction(id: String = "", rewriteMappings: Map[String, String]= Map()) {

  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  def apply(s: State, verbose: Boolean = false): (List[State], List[State])

  def getIdHash = id.hashCode
}

