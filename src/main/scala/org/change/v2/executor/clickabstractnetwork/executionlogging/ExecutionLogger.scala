package org.change.v2.executor.clickabstractnetwork.executionlogging

import java.io.{FileOutputStream, PrintWriter}

import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.memory.{TagExp, State}
import org.change.v2.analysis.processingmodels.instructions.{InstructionBlock, :==:, Constrain}
import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import org.change.v2.analysis.memory.jsonformatters.StateToJson._
import org.change.v2.validation.RunConfig
import spray.json._
import java.io.File

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

    val output = new PrintWriter(new FileOutputStream(new File("out.json")))
    output.println(ctx.toJson.prettyPrint)
    output.close()

  }
}

object ModelValidation extends ExecutionLogger {
  override def log(ctx: ClickExecutionContext): Unit = if (ctx.isDone) {
    import org.change.v2.analysis.memory.jsonformatters.ConcreteExecutionContextToJson._

    val output = new PrintWriter(new FileOutputStream(new File("out.json")))
    output.println(ctx.toJson.prettyPrint)
    output.close()


    import org.change.v2.util.canonicalnames._

    val (reachedDst, stuckSomewhere) = ctx.stuckStates
      .partition(_.location.toLowerCase().contains("dst"))

    println("Trying those reaching the destination")
    reachedDst.map({ s =>
      val inputExample = RunConfig.mateiInputFlowFromState(s)
      println(inputExample)

      RunConfig.getMateiOutputAsExample(inputExample) match {
        case Some(validationInput) => println(verifyState(validationInput)(s))
        case None => println("Bad output from matei.")
      }
    })

    println("Trying those stuck")
    stuckSomewhere.map({ s =>
      val inputExample = RunConfig.mateiInputFlowFromState(s)
      println(inputExample)

      if (RunConfig.getMateisOutput(inputExample).contains("{}"))
        println("Stuck state got stuck IRL")
      else
        println("Hmm apparently it escaped")
    })
  }

  def verifyState(concreteValues: Map[TagExp, Long])(s: State): Option[String] = {
    val afterConcreteConstraintApplication = InstructionBlock(concreteValues.map({kv =>
      val (what, withWhat) = kv
      Constrain(what, :==:(ConstantValue(withWhat)))
    }))(s)

    if (afterConcreteConstraintApplication._2.isEmpty)
      None
    else
      Some(afterConcreteConstraintApplication._2.head.errorCause.get)
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
