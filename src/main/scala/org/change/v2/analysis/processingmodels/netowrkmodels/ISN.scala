package org.change.v2.analysis.processingmodels.netowrkmodels

import org.change.v2.analysis.expression.concrete.nonprimitive.Plus
import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.memory.Value
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.processingmodels.Block

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class ISN(delta: Value) extends Block{


  override def process(s: State): List[State] = {
    val m = s.memory

    val nm = m.REWRITE("Seq", Plus(m.FGET("Seq"), delta))

    List(State(nm.get, s.history))
  }

}

object ISN {

  def apply(delta: Int) = new ISN(Value(ConstantValue(delta)))

  def apply() = new ISN(Value(SymbolicValue()))

}
