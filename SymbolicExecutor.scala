package org.change.symbolicexec.executors

import org.change.symbolicexec._
import org.change.symbolicexec.blocks.ProcessingBlock
import org.change.symbolicexec.executorhooks._
import parser.generic.{NetworkConfig, PathComponent}

class SymbolicExecutor(val processingBlocks: Map[String, ProcessingBlock],
                       val links: Map[PathLocation, PathLocation]) {

  override def toString = "Processing elements:\n" +
    processingBlocks.map(_._2.toString()).mkString("\n") +
    "\nLinks:\n" +
    links.mkString("\n")

  /**
   * This run one step of symbolic execution.
   * @param paths Currently explored paths.
   * @return Next explored paths.
   */
  private def step(paths: List[Path]): (List[Path], List[Path]) = {
    //    Only valid paths get attention.
    val (valid, invalid) = paths.partition(_.valid)
    //    Reduce verification rules.
    val afterConstraintReduction = valid.map(_.performVerification)
    //    Select those that can go across links.
    val (needPropagation, others) = afterConstraintReduction.partition(_.location.accessPointType == Output)
    //    Propagate across links.
    val (propagateable, stuck) = needPropagation.partition(p => links.contains(p.location))
    //    Move across links.
    val afterPropagation = propagateable.map(p => p.move(links(p.location)))

    // Warning: We should assert no paths on an output port in existence

    //    Invoke processing of every path.
    val afterProcessing = (afterPropagation ++ others).map(p => {
      processingBlocks(p.location.processingBlockId).process(p)
    }).flatten.toList

    (afterProcessing, invalid ++ stuck)
  }

  def execute(hook: HookFunction)(input: List[Path]): List[Path] = if (! input.isEmpty) {
    val (next, stuck) = step(input)
    hook(input, next, stuck)
    stuck ++ execute(hook)(next)
  } else {
    Nil
  }

  def executeAndLog(input: List[Path]): List[Path] = execute(printHook)(input)
  def executeAndLog(input: Path): List[Path] = executeAndLog(List(input))
}

object SymbolicExecutor {
  def apply(parsedModel: NetworkConfig, vmId:String = "vm"): SymbolicExecutor = new SymbolicExecutor(
    elementsToProcessingBlocksexecu(parsedModel).map(e => (e._1._2, e._2)),
    (for {
      segm: List[PathComponent] <- parsedModel.paths
    } yield {
      (for {
        window <- segm.sliding(2)
        e1 = window.head
        e2 = window.last
      } yield (PathLocation(vmId, e1._1, e1._3, Output), PathLocation(vmId, e2._1, e2._2, Input))).toList
    }).flatten.toMap
  )
}