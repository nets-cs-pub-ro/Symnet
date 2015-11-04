package org.change.v2.executor.clickabstractnetwork

import org.change.symbolicexec.verification.Rule
import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.analysis.processingmodels.instructions.InstructionBlock
import org.change.v2.analysis.processingmodels.{LocationId, Instruction, State}
import org.change.v2.executor.clickabstractnetwork.verificator.PathLocation

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 *
 * An execution context is determined by the instructions it can execute and
 * a set of states that were explored.
 *
 * A port is an Int, that maps to an instruction.
 *
 */
class ClickExecutionContext(
                           val instructions: Map[LocationId, Instruction],
                           val links: Map[LocationId, LocationId],
                           val okStates: List[State],
                           val failedStates: List[State],
                           val stuckStates: List[State],
                           val checkInstructions: Map[LocationId, Instruction] = Map.empty
) {

  def +(that: ClickExecutionContext) = new ClickExecutionContext(
    this.instructions ++ that.instructions,
    this.links ++ that.links,
    this.okStates ++ that.okStates,
    this.failedStates ++ that.failedStates,
    this.stuckStates ++ that.stuckStates,
    this.checkInstructions ++ that.checkInstructions
  )

  def isDone: Boolean = okStates.isEmpty

  def untilDone(verbose: Boolean): ClickExecutionContext = if (isDone) this else this.execute(verbose).untilDone(verbose)

  def execute(verbose: Boolean = false): ClickExecutionContext = {
    val (ok, fail, stuck) = (for {
      sPrime <- okStates
      s = if (links contains sPrime.location)
          sPrime.forwardTo(links(sPrime.location))
        else
          sPrime
      stateLocation = s.location
    } yield {
        if (instructions contains stateLocation) {
//          Apply instructions
          val r1 = instructions(stateLocation)(s, verbose)
//          Apply check instructions on output ports
          val (toCheck, r2) = r1._1.partition(s => checkInstructions.contains(s.location))
          val r3 = toCheck.map(s => checkInstructions(s.location)(s,verbose)).unzip
          (r2 ++ r3._1.flatten, r1._2 ++ r3._2.flatten, Nil)
        } else
          (Nil, Nil, List(s))
      }).unzip3

      new ClickExecutionContext(instructions,
        links,
        ok.flatten,
        failedStates ++ fail.flatten,
        stuckStates ++ stuck.flatten,
        checkInstructions
      )
  }



  // TODO: MOve this elsewhere, and allow some sort of customization.
  private def verboselyStringifyStatesWithExample(ss: List[State]): String = ss.zipWithIndex.map( si =>
    "State #" + si._2 + "\n\n" +
      si._1.history.reverse.mkString("\n") +
      si._1.instructionHistory.reverse.mkString("\n") + "\n\n" +
      si._1.memory.verboseToString)
    .mkString("\n")

  def stringifyStates(includeOk: Boolean = true, includeStuck: Boolean = true, includeFailed: Boolean= true) = {
    (if (includeOk)
      s"Ok states (${okStates.length}):\n" + ClickExecutionContext.verboselyStringifyStates(okStates)
    else
      "") +
    (if (includeStuck)
      s"\nStuck states (${stuckStates.length}):\n" + ClickExecutionContext.verboselyStringifyStates(stuckStates)
    else
      "") +
    (if (includeFailed)
      s"\nFailed states (${failedStates.length}): \n" + ClickExecutionContext.verboselyStringifyStates(failedStates)
    else
      "")
  }

  def concretizeStates: String = (stuckStates ++ okStates).map(_.memory.concretizeSymbols).mkString("\n----------\n")

  /**
   * TODO: This stringification should stay in a different place.
   * @param includeOk
   * @param includeStuck
   * @param includeFailed
   * @return
   */
  def verboselyStringifyStates(includeOk: Boolean = true, includeStuck: Boolean = true, includeFailed: Boolean= true) = {
    (if (includeOk)
      s"Ok states (${okStates.length}):\n" + verboselyStringifyStatesWithExample(okStates)
    else
      "") +
      (if (includeStuck)
        s"Stuck states (${stuckStates.length}):\n" + verboselyStringifyStatesWithExample(stuckStates)
      else
        "") +
      (if (includeFailed)
        s"Failed states (${failedStates.length}): \n" + verboselyStringifyStatesWithExample(failedStates)
      else
        "")
  }
}

object ClickExecutionContext {

  def verboselyStringifyStates(ss: List[State]): String = ss.zipWithIndex.map( si =>
    "State #" + si._2 + "\n\n" + si._1.instructionHistory.reverse.mkString("\n") + "\n\n" + si._1.toString)
    .mkString("\n")

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
    ctxes.foldLeft(new ClickExecutionContext(
      Map.empty,
      links,
      // TODO: Should be configurable
      startStates.toList,
      Nil,
      Nil,
      checkInstructions
    ))(_ + _)
  }
}
