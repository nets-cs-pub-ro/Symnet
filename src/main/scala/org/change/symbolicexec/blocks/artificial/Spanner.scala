package org.change.symbolicexec.blocks.artificial

import org.change.symbolicexec.Path
import org.change.symbolicexec.blocks.NoopProcessingBlock

class Spanner(id: String) extends NoopProcessingBlock(id, 1, 0) {

  val activeOutputPorts: scala.collection.mutable.SortedSet[Int] = scala.collection.mutable.SortedSet[Int]()
  val freeOutputPorts: scala.collection.mutable.SortedSet[Int] = scala.collection.mutable.SortedSet[Int]()

  var nextAvail: Int = 0

  def add: Int =
    if (freeOutputPorts.nonEmpty) {
      val fst = freeOutputPorts.firstKey
      freeOutputPorts.remove(fst)
      activeOutputPorts.add(fst)
      fst
    } else {
      activeOutputPorts.add(nextAvail)
      nextAvail += 1
      nextAvail - 1
    }

  def remove(port: Int): Unit = {
    if (activeOutputPorts.remove(port))
      freeOutputPorts.add(port)
  }

  override def process(p: Path): List[Path] = (for {
    exitPoint <- activeOutputPorts
  } yield {
    moveToOutputPort(p, exitPoint)
  }).toList
}
