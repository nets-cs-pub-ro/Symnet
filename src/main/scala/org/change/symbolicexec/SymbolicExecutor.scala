package org.change.symbolicexec

import parser.generic.{PathComponent, NetworkConfig}

/**
 * The execution process is governed by a SymbolicExecutor process.
 *
 * It's job is to:
 * - execute runnable processing blocks (those reached by a certain path)
 * - advance paths between connected modules (cross the links)
 * - enforce integrity and security constraints
 * - [eventually] filter unwanted paths
 *
 * @param parsedModel
 */
class SymbolicExecutor(parsedModel: NetworkConfig) {

  private val processingBlocks: Map[String, ProcessingBlock] = parsedModel.elements.map( e => (e._1, e._2.toProcessingBlock) )
  private val links: Map[PathLocation, PathLocation]=  (for {
    segm: List[PathComponent] <- parsedModel.paths
  } yield {
    (for {
      window <- segm.sliding(2)
      e1 = window.head
      e2 = window.last
    } yield (PathLocation(e1._1, e1._3, Output), PathLocation(e2._1, e2._2, Input))).toList
//  Warning: maybe a mutable list would be better
  }).foldLeft(Nil: List[(PathLocation, PathLocation)])( (acc, l) => acc ++ l ).toMap

  override def toString = "Processing elements:\n" +
    processingBlocks.map(_._2.toString()).mkString("\n") +
    "\nLinks:\n" +
    links.mkString("\n")

}
