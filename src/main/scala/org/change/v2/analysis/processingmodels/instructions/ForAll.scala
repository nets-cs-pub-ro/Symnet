package org.change.v2.analysis.processingmodels.instructions

import scala.util.matching.Regex
import org.change.v2.analysis.processingmodels._
import org.change.v2.analysis.expression.concrete.nonprimitive.:@

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class ForAll(bindingPattern: (String, Regex), instrs: List[Instruction])
  extends Instruction {

  override def apply(s: State, v: Boolean): (List[State], List[State]) = {
    var matched = false
    val (succ,fail) = (for {
      binding <- s.memory.symbols.keys
      if bindingPattern._2.unapplySeq(binding).isDefined
    } yield {
        matched = true
        InstructionBlock(
          Allocate(bindingPattern._1),
          Assign(bindingPattern._1, :@(binding)),

          InstructionBlock(instrs),

          Deallocate(bindingPattern._1)
        )(s)
      }).toList.unzip

    if (matched)
      (succ.flatten, fail.flatten)
    else
      (List(s), Nil)
  }

}

object ForAll {

  implicit class StringImprovements(val s: String) {
    def :<-(pattern: String): (String, Regex) =
      (":" + s, pattern.r)
  }

  def apply(bindingPattern: (String, Regex))(instrs: Instruction*): ForAll =
    ForAll(bindingPattern, instrs.toList)
}
