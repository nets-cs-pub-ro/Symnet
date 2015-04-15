package org.change.v2.runners

import org.change.v2.analysis.memory.MemorySpace
import org.change.v2.analysis.processingmodels.netowrkmodels.ISN
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.z3.Z3Util

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object BasicProcessing {

  def main(args: Array[String]): Unit = {

    val initial = State(MemorySpace.cleanWithSymolics(List("Seq")))

    val check0 = Z3Util.z3Context.mkGE(initial.memory.FGET("Seq").toZ3, Z3Util.z3Context.mkInt(0, Z3Util.intSort))

    val finalState = ISN(10).process(initial).head

    val seq = finalState.memory.FGET("Seq").toZ3

    val check1 = Z3Util.z3Context.mkGE(seq, Z3Util.z3Context.mkInt(0, Z3Util.intSort))
    val check2 = Z3Util.z3Context.mkLT(seq, Z3Util.z3Context.mkInt(10, Z3Util.intSort))

    Z3Util.solver.assertCnstr(Z3Util.z3Context.mkAnd(check0
      ,check1
      ,check2
    ))

    Z3Util.solver.check() match {
      case (None) => println("Z3 failed. The reason is: " + Z3Util.solver.getReasonUnknown())
      case Some(false) => println("Unsat.")
      case Some(true) => {
        val model = Z3Util.solver.getModel()
        println("Ok")
      }
    }

  }

}
