/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
package object transformation {

  import expression.Expression

  type ExpressionRewriter = (Expression => Expression)

}
