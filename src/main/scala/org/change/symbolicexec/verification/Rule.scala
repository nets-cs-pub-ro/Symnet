package org.change.symbolicexec.verification

import org.change.symbolicexec.{Path, MemoryState, PathLocation}

case class Rule(where: PathLocation,
                whatTraffic: Option[MemoryState] = None,
                whatInvariants: Option[List[String]] = None,
                invariantTimeStamps: Map[String, Int] = Map()) extends Verifiable {

  override def verifyTraffic(p: MemoryState): Boolean = whatTraffic match {
    case Some(description) => p supersetOf description
    case None => true
  }

  override def verifyInvariants(p: Path): Boolean =
    if (invariantTimeStamps.isEmpty) true
    else whatInvariants match {
      case Some(invariants) => invariants.forall(i => invariantTimeStamps(i) == p.symbolWriteCount(i) )
      case None => true
    }
}

object Rule {
  def apply(r: Rule, stamps: Map[String, Int]): Rule = Rule(r.where, r.whatTraffic, r.whatInvariants, stamps)
}
