package org.change.symbolicexec.blocks

import org.change.symbolicexec.{Output, Path, PathLocation}

case class NoopProcessingBlock(id: String, entryCount: Int, exitCount: Int) extends ProcessingBlock {

  def process(p: Path): List[Path] = (for {
    exitPoint <- 0 until exitCount
  } yield {
    moveToOutputPort(p, exitPoint)
  }).toList

}
