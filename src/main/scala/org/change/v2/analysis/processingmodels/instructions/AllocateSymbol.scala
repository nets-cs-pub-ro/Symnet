package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.processingmodels.{State, Instruction}
import org.change.v2.analysis.memory.{TagExp, Intable}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class AllocateSymbol(id: String) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) = {
    optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Cannot allocate $id") (s => {
      s.memory.Allocate(id)
    })
  }
}

case class AllocateRaw(a: Intable, size: Int) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) = a(s) match {
    case Some(int) => optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Cannot allocate at $a size $size")(s => {
      s.memory.Allocate(int, size)
    })
    case None => Fail(TagExp.brokenTagExpErrorMessage)(s,v)
  }
}

object Allocate {
  def apply(id: String): Instruction = AllocateSymbol(id)
  def apply(a: Intable, size: Int): Instruction = AllocateRaw(a, size)
}