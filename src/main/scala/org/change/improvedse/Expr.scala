package org.change.improvedse

import org.change.symbolicexec.Constraint

/**
 * Created by radu on 22.01.2015.
 */
class Expr(constraints: List[Constraint]) extends MemObject(constraints) {

  override def eval = None

  override def forceEval = throw new UnsupportedOperationException("Can't force eval a symbolic expression")

  override def constrain(c: Constraint): MemObject = new Expr(c :: constraints)

  override def constrain(cs: List[Constraint]): MemObject = new Expr(cs ++ constraints)

  override def isSymbolicExpression: Boolean = false
}
