package org.change.symbolicexec.networkgraph

import org.change.symbolicexec.blocks.artificial.Spanner

class Platform(id: String) {

  val entry = new Spanner(id+"-entry")
  val exit = new Spanner(id+"-exit")



}
