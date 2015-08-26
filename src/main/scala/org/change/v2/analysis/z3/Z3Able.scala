package org.change.v2.analysis.z3

import z3.scala.{Z3Solver, Z3AST}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 *
 * An entity convertible to Z3 AST.
 */
trait Z3Able {

  def toZ3(solver: Option[Z3Solver] = None): (Z3AST, Option[Z3Solver])

}
