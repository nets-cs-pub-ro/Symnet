package org.change.v2.analysis.z3

import z3.scala.{Z3Context, Z3Solver, Z3AST, Z3Sort}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 *
 * An entity convertible to Z3 AST.
 */
trait Z3Able {

  def toZ3(solver: Option[Z3Solver] = None): (Z3AST, Option[Z3Solver])

  def toZ3(context: Z3Context, solver: Z3Solver, sort: Z3Sort): (Z3AST, Z3Solver) = ???

}
