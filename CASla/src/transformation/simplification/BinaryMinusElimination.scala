package transformation.simplification

import expression.Expression
import expression.arithmetic.{UnaryMinus, NaryPlus, Minus}
import transformation.ExpressionRewriter

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object BinaryMinusElimination extends ExpressionRewriter {
  override def apply(v1: Expression): Expression = v1 match {
    case Minus(a, b) => NaryPlus(a, UnaryMinus(b))
    case _ => v1
  }
}
