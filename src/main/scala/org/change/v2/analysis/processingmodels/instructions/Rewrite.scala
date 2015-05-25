package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.expression.concrete.nonprimitive.{Ref, Minus, Plus}
import org.change.v2.analysis.processingmodels.{State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Rewrite(id: String, exp: Expression) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State): (List[State], List[State]) = {
    optionToStatePair(s, "Error during 'rewrite'") {
      _.memory.REWRITE(id, exp)
    }
  }
}

case class ContextualRewrite(id: String, exp: ContextTransformable) extends Instruction {
  /**
   *
   * A state processing block produces a set of new states based on a previous one.
   *
   * @param s
   * @return
   */
  override def apply(s: State): (List[State], List[State]) = {
    optionToStatePair(s, "Error during 'rewrite'") {
      _.memory.REWRITE(id, exp transform s)
    }
  }
}

trait ContextTransformable {
  def transform(context: State): Expression
}

case class :+:(a: String, b: String) extends ContextTransformable {
  override def transform(context: State): Expression = {
    val va = context.memory.FGET(a)
    val vb = context.memory.FGET(b)
    Plus(va, vb)
  }
}
case class :-:(a: String, b: String) extends ContextTransformable {
  override def transform(context: State): Expression = {
    val va = context.memory.FGET(a)
    val vb = context.memory.FGET(b)
    Minus(va, vb)
  }
}
case class :->(what: String) extends ContextTransformable {
  override def transform(context: State): Expression = Ref(context.memory.FGET(what))
}