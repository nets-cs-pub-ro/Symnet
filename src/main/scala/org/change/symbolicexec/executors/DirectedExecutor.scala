package org.change.symbolicexec.executors

import org.change.symbolicexec.blocks.ProcessingBlock
import org.change.symbolicexec.executorhooks._
import org.change.symbolicexec.networkgraph.NetworkNode
import parser.generic.NetworkConfig
import org.change.symbolicexec._

class DirectedExecutor(val processingBlocks: Map[(String, String), ProcessingBlock]) {

  def step(paths: List[(Path, NetworkNode)]): (List[(Path, NetworkNode)], List[(Path, NetworkNode)]) = {
    //    Only valid paths get attention.
    val (valid, invalid) = paths.partition(_._1.valid)
    //    Reduce verification rules.
    val afterConstraintReduction = valid.map(pn => (pn._1.performVerification, pn._2))
    //    Select those that can go across links.
    val (needPropagation, others) = afterConstraintReduction.partition(_._1.location.accessPointType == Output)
    //    Propagate across links.

    // TODO: This is where propagation across platformm links occurs.
    val (propagateable, stuck) = needPropagation.partition(pn => pn._2.eLinks.contains(pn._1.locationPort))
    //    Move across node links.
    val afterPropagation = propagateable.map(pn => {
      val prevLocation = pn._1.location
      val nextNode = pn._2.eLinks(prevLocation.accessPointOrd)
      val nextPathLocation = PathLocation(nextNode._1.vmId, nextNode._1.elementId, nextNode._2, Input)

      (pn._1.move(nextPathLocation), nextNode._1)
    })
    //    Invoke processing of every path.
    val (needProcessing, nodes) = (afterPropagation ++ others).unzip

    val afterProcessing = needProcessing.map(p => {
      processingBlocks((p.location.vmId, p.location.processingBlockId)).process(p)
    }).zip(nodes).map(rsp => rsp._1.map(rs => (rs, rsp._2))).flatten.toList

    (afterProcessing, invalid ++ stuck)
  }

  def execute(hook: HookFunction)(input: List[(Path, NetworkNode)]): List[(Path, NetworkNode)] = if (! input.isEmpty) {
    val (next, stuck) = step(input)
    hook(input.map(_._1), next.map(_._1), stuck.map(_._1))
    stuck ++ execute(hook)(next)
  } else {
    Nil
  }

  def executeAndLog(input: List[(Path, NetworkNode)]): List[(Path, NetworkNode)] = execute(printHook)(input)
  def executeAndLog(input: (Path, NetworkNode)): List[(Path, NetworkNode)] = executeAndLog(List(input))

  def grow(otherExecutor: DirectedExecutor) = new DirectedExecutor(processingBlocks ++ otherExecutor.processingBlocks)
  def grow(blocks: Map[(String, String), ProcessingBlock]) = new DirectedExecutor(processingBlocks ++ blocks)
  def grow(otherNetwork: NetworkConfig, vmId: String) = {
    val newElems = elementsToProcessingBlocks(otherNetwork, vmId)
    new DirectedExecutor(processingBlocks ++ newElems)
  }
}

object DirectedExecutor {
  def apply(parsedModel: NetworkConfig, vmId: String): DirectedExecutor = new DirectedExecutor(elementsToProcessingBlocks(parsedModel, vmId))
}
