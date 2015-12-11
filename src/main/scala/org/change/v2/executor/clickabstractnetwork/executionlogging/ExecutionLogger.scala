package org.change.v2.executor.clickabstractnetwork.executionlogging

import org.change.v2.analysis.memory.State
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.change.v2.analysis.memory.jsonformatters.StateToJson._
import spray.json._

/**
 * A small gift from radu to symnetic.
 */
trait ExecutionLogger {
  def log(ctx: ClickExecutionContext): Unit = {}
}

object NoLogging extends ExecutionLogger

object JsonLogger extends ExecutionLogger {
  override def log(ctx: ClickExecutionContext): Unit = if (ctx.isDone) {
    import org.change.v2.analysis.memory.jsonformatters.ExecutionContextToJson._
    println(ctx.toJson.prettyPrint)
  }
}

// TODO: This needs some serios massage.
object OldStringifier extends ExecutionLogger {

  override def log(ctx: ClickExecutionContext): Unit = if (ctx.isDone)
      println(verboselyStringifyStates(ctx))

  def verboselyStringifyStatesWithExample(ss: List[State]): String = ss.zipWithIndex.map( si =>
    "State #" + si._2 + "\n\n" +
      si._1.history.reverse.mkString("\n") +
      si._1.instructionHistory.reverse.mkString("\n") + "\n\n" +
      si._1.memory.verboseToString)
    .mkString("\n")

  def stringifyStates(ctx: ClickExecutionContext,
                      includeOk: Boolean = true,
                      includeStuck: Boolean = true,
                      includeFailed: Boolean= true) = {
    (if (includeOk)
      s"Ok states (${ctx.okStates.length}):\n" + verboselyStringifyStates(ctx.okStates)
    else
      "") +
      (if (includeStuck)
        s"\nStuck states (${ctx.stuckStates.length}):\n" + verboselyStringifyStates(ctx.stuckStates)
      else
        "") +
      (if (includeFailed)
        s"\nFailed states (${ctx.failedStates.length}): \n" + verboselyStringifyStates(ctx.failedStates)
      else
        "")
  }

  /**
   * @param includeOk
   * @param includeStuck
   * @param includeFailed
   * @return
   */
  def verboselyStringifyStates(ctx: ClickExecutionContext,
                               includeOk: Boolean = true,
                               includeStuck: Boolean = true,
                               includeFailed: Boolean= true) = {
    (if (includeOk)
      s"Ok states (${ctx.okStates.length}):\n" + verboselyStringifyStatesWithExample(ctx.okStates)
    else
      "") +
      (if (includeStuck)
        s"Stuck states (${ctx.stuckStates.length}):\n" + verboselyStringifyStatesWithExample(ctx.stuckStates)
      else
        "") +
      (if (includeFailed)
        s"Failed states (${ctx.failedStates.length}): \n" + verboselyStringifyStatesWithExample(ctx.failedStates)
      else
        "")
  }

  def verboselyStringifyStates(ss: List[State]): String = ss.zipWithIndex.map( si =>
    "State #" + si._2 + "\n\n" + si._1.instructionHistory.reverse.mkString("\n") + "\n\n" + si._1.toString)
    .mkString("\n")
}
