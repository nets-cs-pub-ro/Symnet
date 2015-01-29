package org.change.improvedse

import org.change.symbolicexec.Constraint
import org.change.symbolicexec.Symbol

/**
 * Created by radu on 22.01.2015.
 */
abstract class Expr(constraints: List[Constraint]) extends MemObject(constraints) {

  override def eval = None

  override def forceEval = throw new UnsupportedOperationException("Can't force eval a symbolic expression")

  override def isSymbolicExpression: Boolean = false
}

case class Ref(symbol: Symbol, version: Int, cts: List[Constraint] = Nil) extends Expr(cts) {

  override def constrain(c: Constraint): MemObject = new Ref(symbol, version, c :: cts)

  override def constrain(cs: List[Constraint]): MemObject = new Ref(symbol, version, cs ++ cts)
}
