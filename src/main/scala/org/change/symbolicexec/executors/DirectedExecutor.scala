package org.change.symbolicexec.executors

import org.change.symbolicexec._
import org.change.symbolicexec.blocks.ProcessingBlock
import org.change.symbolicexec.executorhooks._
import org.change.symbolicexec.networkgraph.NetworkNode
import parser.generic.NetworkConfig

class DirectedExecutor(val model: ExecutableModel) {

  def step(paths: List[Path]): (List[Path], List[Path]) = {
    //    Only valid paths get attention.
    val (valid, invalid) = paths.partition(_.valid)
    //    Reduce verification rules.
    val afterConstraintReduction = valid.map(_.performVerification)
    //    Select those that can go across links.
    val (needPropagation, others) = afterConstraintReduction.partition(_.location.accessPointType == Output)
    //    Propagate across links.

    // TODO: This is where propagation across platformm links occurs.
    val (propagateable, stuck) = needPropagation.partition(p => model((p.locationVm, p.locationElement)).eLinks.contains(p.locationPort))
    //    Move across node links.
    val afterPropagation = propagateable.map(p => {
      val next = model((p.locationVm, p.locationElement)).eLinks(p.locationPort)
      val nextPathLocation = PathLocation(next._2._1, next._2._2, next._1, Input)
      p.move(nextPathLocation)
    })

    //    Invoke processing of every path.
    val needProcessing = (afterPropagation ++ others)


    val afterProcessing = needProcessing.flatMap(p => model((p.locationVm, p.locationElement)).processingBlock.process(p))

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

object DirectedExecutor {
  def apply(networkConfig: NetworkConfig, vmId: String) = new DirectedExecutor(elementsToExecutableModel(networkConfig, vmId))
}
