package org.change.symbolicexec.blocks

import org.change.symbolicexec._
import parser.generic.ConfigParameter

class IPFilterBlock(id: String, params: List[ConfigParameter]) extends
  NoopProcessingBlock(id, 1, params.length) {

  override def process(p: Path): List[Path] = (for {
    exitPoint <- 0 until exitCount
  } yield {
    p.modifyAndMove(_.constrain("IP-Dst" ,List(E(10), GT(15))) ,PathLocation(id, exitPoint, Output))
  }).toList

}
