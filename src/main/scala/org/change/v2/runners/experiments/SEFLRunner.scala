package org.change.v2.runners.experiments

import java.io.{FileOutputStream, PrintStream, File}

import org.change.v2.analysis.expression.concrete.nonprimitive._
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.processingmodels.State
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext

object SEFLRunner {

  lazy val output = new PrintStream(new FileOutputStream(new File("sefl.output")))

  def main (args: Array[String]){

    val (successful, failed) = ex0

//    output.println(s"OK States (${successful.length}}):\n" + ClickExecutionContext.verboselyStringifyStates(successful))
//    output.println(s"\nFailed States (${failed.length}}):\n" + ClickExecutionContext.verboselyStringifyStates(failed))

  }

  def ex0: (List[State], List[State]) = {
    val code = InstructionBlock (
      Assign("a", SymbolicValue()),
      Assign("zero", ConstantValue(0)),
      // State that a is positive
      Constrain("a", :>:(:@("zero"))),
      // Compute the sum
      Assign("sum", :+:(:@("a"), :@("zero"))),
      // We want the sum to be 0, meaning a should also be zero - impossible
      Constrain("sum", :==:(:@("zero")))
    )

    code(State.clean, true)
  }
}


