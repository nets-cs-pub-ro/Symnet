package org.change.symbolicexec

case class NoopProcessingBlock(id: String, entryCount: Int, exitCount: Int) extends ProcessingBlock {

  def process(p: Path): List[Path] = (for {
    exitPoint <- 0 to exitCount
  } yield {
    p.move(PathLocation(id, exitPoint, Output))
  }).toList

}
