package org.change.symbolicexec.verification

import org.change.symbolicexec.{Path, MemoryState, PathLocation}

case class Rule(where: PathLocation, whatTraffic: Option[MemoryState] = None) extends Verifiable {

  override def verifyTraffic(p: MemoryState): Boolean = whatTraffic match {
    case Some(description) => p supersetOf description
    case None => true
  }

  override def verifyInvariants(p: Path): Boolean = true

}
