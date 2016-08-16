package org.change.v2.runners.sefl

import java.io.{File, FileOutputStream, PrintStream}

import org.change.v2.analysis.memory.State
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.change.v2.executor.clickabstractnetwork.executionlogging.JsonLogger

/**
  * A small gift from radu to symnetic.
  */
object SEFLExecutor {

  def apply(instructions: Map[String, Instruction], links: Map[String, String]): (List[State], List[State]) = {
    val result = new ClickExecutionContext(instructions,
      links,
      List(State.clean.forwardTo(instructions.keys.head)),
      Nil,
      Nil,
      logger = JsonLogger).untilDone(true)
    (result.stuckStates, result.failedStates)
  }

  def main(args: Array[String]): Unit = {
    val states = apply(SEFLCodeAndLinks.i, SEFLCodeAndLinks.l)

    println("Possible: " + states._1.length)
    println("Impossible: " + states._2.length)

    val (successful, failed) = (states._1, states._2)
    val output = new PrintStream(new FileOutputStream(new File("sefl.output")))

    output.println(
      successful.map(_.jsonString).mkString("Successful: {\n", "\n", "}\n") +
        failed.map(_.jsonString).mkString("Failed: {\n", "\n", "}\n")
    )

    output.close()

    println("Check output @ sefl.output")
  }

}
