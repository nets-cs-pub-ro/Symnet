package org.change.v2.analysis.processingmodels.netowrkmodels

import org.change.v2.analysis.expression.concrete.nonprimitive.{Minus, Plus}
import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.memory.{MemorySpace, Value}
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.processingmodels.Block

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class ISNR(delta: Value) extends Block {


  override def process(s: State): List[State] = {
    val m = s.memory

    val nm = m.REWRITE("delta", delta.e).flatMap((x: MemorySpace) =>
      x.REWRITE("Seq", Plus(m.FGET("Seq"), delta)))

    List(State(nm.get, s.history))
  }

}

object ISNR {

  def apply(delta: Int) = new ISNR(Value(ConstantValue(delta)))

  def apply() = new ISNR(Value(SymbolicValue()))

}

object ReverseISNR extends Block {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def process(s: State): List[State] = {
    val m = s.memory

    val nm = m.REWRITE("Seq", Minus(m.FGET("Seq"), m.FGET("delta")))

    List(State(nm.get, s.history))
  }
}
