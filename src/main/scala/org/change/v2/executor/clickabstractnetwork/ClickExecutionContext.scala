package org.change.v2.executor.clickabstractnetwork

import org.change.symbolicexec.verification.Rule
import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.analysis.memory.State
import org.change.v2.analysis.processingmodels.instructions.{InstructionBlock, NoOp}
import org.change.v2.analysis.processingmodels.{LocationId, Instruction}
import org.change.v2.executor.clickabstractnetwork.executionlogging.{NoLogging, ExecutionLogger}
import org.change.v2.executor.clickabstractnetwork.verificator.PathLocation
import org.change.utils.abstractions._

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 *
 * An execution context is determined by the instructions it can execute and
 * a set of states that were explored.
 *
 * A port is a String id, that maps to an instruction.
 */
case class ClickExecutionContext(
                           instructions: Map[LocationId, Instruction],
                           links: Map[LocationId, LocationId],
                           okStates: List[State],
                           failedStates: List[State],
                           stuckStates: List[State],
                           checkInstructions: Map[LocationId, Instruction] = Map.empty,
                           logger: ExecutionLogger = NoLogging
) {
  def setLogger(newLogger: ExecutionLogger): ClickExecutionContext = copy(logger = newLogger)

  /**
   * Merges two execution contexts.
   * @param that
   * @return
   */
  def +(that: ClickExecutionContext) = copy(
    this.instructions ++ that.instructions,
    this.links ++ that.links,
    this.okStates ++ that.okStates,
    this.failedStates ++ that.failedStates,
    this.stuckStates ++ that.stuckStates,
    this.checkInstructions ++ that.checkInstructions
  )

  /**
   * Is there any state further explorable ?
   * @return
   */
  def isDone: Boolean = okStates.isEmpty

  /**
   * Calls execute until nothing can be explored further more. (The result is a done Execution Context)
   * @param verbose
   * @return
   */
  def untilDone(verbose: Boolean): ClickExecutionContext = if (isDone) this else this.execute(verbose).untilDone(verbose)

  def execute(verbose: Boolean = false): ClickExecutionContext = {
    val (ok, fail, stuck) = (for {
      sPrime <- okStates
    } yield {
      val s = if (links contains sPrime.location)
                sPrime.forwardTo(links(sPrime.location))
              else
                sPrime
      val stateLocation = s.location
      // FIXME: This is just a workaround.
      val instruction = instructions.getOrElse(stateLocation, NoOp)

      // Apply instructions
      val r1 = instruction(s, verbose)
      // Apply check instructions on output ports
      val (toCheck, r2) = r1._1.partition(s => checkInstructions.contains(s.location))
      val r3 = toCheck.map(s => checkInstructions(s.location)(s,verbose)).unzip
      (r2 ++ r3._1.flatten, r1._2 ++ r3._2.flatten, Nil)
    }).unzip3

    useAndReturn(copy(
      okStates = ok.flatten,
      failedStates = failedStates ++ fail.flatten,
      stuckStates = stuckStates ++ stuck.flatten
    ), {ctx: ClickExecutionContext => logger.log(ctx)})
  }

  // TODO: Move to a logger
  def concretizeStates: String = (stuckStates ++ okStates).map(_.memory.concretizeSymbols).mkString("\n----------\n")

}

object ClickExecutionContext {

  /**
   * Builds a symbolic execution context out of a single click config file.
   *
   * @param networkModel
   * @param verificationConditions
   * @param includeInitial
   * @return
   */
  def fromSingle( networkModel: NetworkConfig,
                  verificationConditions: List[List[Rule]] = Nil,
                  includeInitial: Boolean = true,
                  initialIsClean: Boolean = false): ClickExecutionContext = {
    // Collect instructions for every element.
    val instructions = networkModel.elements.values.foldLeft(Map[LocationId, Instruction]())(_ ++ _.instructions)
    // Collect check instructions corresponding to network rules.
    val checkInstructions = verificationConditions.flatten.map( r => {
        networkModel.elements(r.where.element).outputPortName(r.where.port) -> InstructionBlock(r.whatTraffic)
      }).toMap
    // Create forwarding links.
    val links = networkModel.paths.flatMap( _.sliding(2).map(pcp => {
      val src = pcp.head
      val dst = pcp.last
      networkModel.elements(src._1).outputPortName(src._3) -> networkModel.elements(dst._1).inputPortName(dst._2)
    })).toMap
    // TODO: This should be configurable.
    val initialStates = if (includeInitial) {
      if (initialIsClean)
        List(State.clean.forwardTo(networkModel.entryLocationId))
      else
        List(State.bigBang.forwardTo(networkModel.entryLocationId))
    } else Nil

    new ClickExecutionContext(instructions, links, initialStates, Nil, Nil, checkInstructions)
  }

  def buildAggregated(
            configs: Iterable[NetworkConfig],
            interClickLinks: Iterable[(String, String, Int, String, String, Int)],
            verificationConditions: List[List[Rule]] = Nil,
            startElems: Option[Iterable[(String, String, Int)]] = None): ClickExecutionContext = {
    // Create a context for every network config.
    val ctxes = configs.map(ClickExecutionContext.fromSingle(_, includeInitial = false))
    // Keep the configs for name resolution.
    val configMap: Map[String, NetworkConfig] = configs.map(c => c.id.get -> c).toMap
    // Add forwarding links between click files.
    val links = interClickLinks.map(l => {
      val ela = l._1 + "-" + l._2
      val elb = l._4 + "-" + l._5
      configMap(l._1).elements(ela).outputPortName(l._3) -> configMap(l._4).elements(elb).inputPortName(l._6)
    }).toMap
    // Collect check instructions corresponding to network rules.
    val checkInstructions = verificationConditions.flatten.map( r => {
      val elementName = r.where.vm + "-" + r.where.element
      configMap(r.where.vm).elements(elementName).outputPortName(r.where.port) -> InstructionBlock(r.whatTraffic)
    }).toMap
    // Create initial states
    val startStates = startElems match {
      case Some(initialPoints) => initialPoints.map(ip =>
        State.bigBang.forwardTo(configMap(ip._1).elements(ip._1 + "-" + ip._2).inputPortName(ip._3)))
      case None => List(State.bigBang.forwardTo(configs.head.entryLocationId))
    }
    // Build the unified execution context.
    ctxes.foldLeft(new ClickExecutionContext(Map.empty, links, startStates.toList, Nil, Nil, checkInstructions))(_ + _)
  }
}
