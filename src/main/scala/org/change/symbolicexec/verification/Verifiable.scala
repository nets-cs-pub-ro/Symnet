package org.change.symbolicexec.verification

import org.change.symbolicexec.{MemoryState, Path}

trait Verifiable {

  def verifyTraffic(p: MemoryState): Boolean = true
  def verifyInvariants(p: Path): Boolean = true

}
