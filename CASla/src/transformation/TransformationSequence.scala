package transformation

import expression.Expression

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class TransformationSequence(ts: ExpressionRewriter*) extends ExpressionRewriter {
  override def apply(initialE: Expression): Expression = ts.foldLeft(initialE)((e, t) => t(e))
}
