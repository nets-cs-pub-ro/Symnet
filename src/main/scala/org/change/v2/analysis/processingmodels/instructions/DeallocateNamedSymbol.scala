package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.memory.{TagExp, Intable}
import org.change.v2.analysis.processingmodels.{State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class DeallocateNamedSymbol(id: String) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) = {
    optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Cannot deallocate $id") (s => {
      s.memory.Deallocate(id)
    })
  }
}

case class DeallocateRaw(a: Intable, size: Int) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State, v: Boolean): (List[State], List[State]) = a(s) match {
    case Some(int) => optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Cannot deallocate @ $a of size $size") (s => {
      s.memory.Deallocate(int, size)
    })
    case None => Fail(TagExp.brokenTagExpErrorMessage).apply(s, v)
  }
}

object Deallocate {
  def apply(id: String): Instruction = DeallocateNamedSymbol(id)
  def apply(a: Intable, size: Int): Instruction =  DeallocateRaw(a, size)
}