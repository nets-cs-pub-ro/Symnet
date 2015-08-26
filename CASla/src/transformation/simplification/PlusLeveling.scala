package transformation.simplification

import expression.Expression
import expression.arithmetic.NaryPlus
import transformation.ExpressionRewriter

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object PlusLeveling extends ExpressionRewriter {
  override def apply(v1: Expression): Expression = v1 match {
    case NaryPlus(es) => es
  }
}
