package org.change.v2.analysis.expression.concrete

import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.processingmodels.instructions.ContextTransformable
import org.change.v2.analysis.z3.Z3Util
import z3.scala.{Z3Solver, Z3AST}

/**
 * Created by radu on 3/24/15.
 */
case class SymbolicValue() extends Expression with ContextTransformable {
  lazy val ast = Z3Util.z3Context.mkConst(id.toString, Z3Util.defaultSort)

  override def toZ3(solver: Option[Z3Solver] = None): (Z3AST, Option[Z3Solver]) = (ast, solver)

  override def transform(context: State): Expression = this
}
