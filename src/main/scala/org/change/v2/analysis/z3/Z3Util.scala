package org.change.v2.analysis.z3

import z3.scala.{Z3Sort, Z3Context, Z3Config}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object Z3Util {

  lazy val z3Context: Z3Context = new Z3Context(new Z3Config("MODEL" -> true))

  private lazy val intSort: Z3Sort = z3Context.mkIntSort()

  /**
   * OPTIMIZE
   * @return
   */
  def solver = z3Context.mkSolver()

  lazy val defaultSort = intSort

  def newContextAndSort(): (Z3Context, Z3Sort) = {
    val ctx = new Z3Context()
    (ctx, ctx.mkIntSort())
  }

}
