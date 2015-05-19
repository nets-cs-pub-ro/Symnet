package org.change.v2.runners

import org.change.v2.analysis.memory.MemorySpace
import org.change.v2.analysis.processingmodels.networkmodels.{ReverseISNR, ISNR}
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.z3.Z3Util

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object BasicProcessing {

  def main(args: Array[String]): Unit = {

    val initial = State(MemorySpace.cleanWithSymolics(List("Seq")))

    val initialSeq = initial.memory.FGET("Seq").toZ3()

//    val check0 = Z3Util.z3Context.mkGE(initialSeq, Z3Util.z3Context.mkInt(0, Z3Util.defaultSort))

    val afterISNR = ISNR(10).apply(initial).head

    val afterISNRseq = afterISNR.memory.FGET("Seq").toZ3()

//    val check1 = Z3Util.z3Context.mkGE(afterISNRseq, Z3Util.z3Context.mkInt(0, Z3Util.defaultSort))
//    val check2 = Z3Util.z3Context.mkLT(afterISNRseq, Z3Util.z3Context.mkInt(10, Z3Util.defaultSort))

    val afterReverse = ReverseISNR.apply(afterISNR).head
//    val afterReverse = afterISNR

    val reversedSeq = afterReverse.memory.FGET("Seq").toZ3()

//    val correctnessCheck = Z3Util.z3Context.mkEq(initialSeq, reversedSeq)

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
