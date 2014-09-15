package org.change.symbolicexec

import org.change.symbolicexec.verification.ReachabilityTestGroup

/**
 * The execution path is the abstraction used for a given network flow
 * passing through the network.
 */
class Path(val history: List[PathLocation] = Nil,
           val memory: Memory = new Memory(),
           val tests: List[ReachabilityTestGroup] = Nil) {

  /**
   * Moving a path requires pushing its new location to the history.
   * @param nextLocation
   * @return
   */
  def move(nextLocation: PathLocation): Path = new Path(nextLocation :: history, memory, tests)

  def modifyWith(f: (Memory) => Memory): Path = new Path(history, f(memory), tests)

  def modifyAndMove(f: (Memory) => Memory, nextLocation: PathLocation) = modifyWith(f).move(nextLocation)

  def valid = memory.valid

  def performVerification: Path = {
    val snapshot = memory.snapshotOfAll
    var modified = false

    val reducedTests = tests.map { group =>
      group.map { test =>
        if ( location == test.head.where && test.head.verifyTraffic(snapshot) && test.head.verifyInvariants(this)) {
          modified = true
          test.tail
        } else {
          test
        }
      }.filter( _.nonEmpty )
    }

    if (modified)
      new Path(history, memory, reducedTests)
    else
      this
  }

  /**
   * The current location of a given path is the head of its history stack.
   * @return
   */
  def location: PathLocation = history.head
  def locationPort = location.accessPointOrd

  override def toString = s"\nPATH (${if (valid) "Valid" else "Invalid"}):\n"+
    s"\nSymbol Table:\n\n${memory.mem.map(kv =>  kv._1 +" > {"+ kv._2.map(_.eval.map(i => s"[${i._1}, ${i._2}]").mkString(" U ")).mkString(" , ")+"}").mkString("\n")}\n"+
    s"\nHistory:\n\n${history.mkString("\n")}\n"+
    s"\nVerification rules:\n\n${tests.mkString("\n")}"
}

object Path {

  /**
   * Blank path.
   * @return
   */
  def apply(): Path = new Path(Nil)

  def cleanWithCanonical(l: PathLocation, testGroups: List[ReachabilityTestGroup] = Nil) =
    new Path(Nil, new Memory(), testGroups).modifyAndMove(
      _ => Memory.withCanonical(),
      l
    )
}

case class PathLocation(vmId: String, processingBlockId: String, accessPointOrd: Int, accessPointType: AccessPointType) {
  override def toString = s"< $vmId :: $processingBlockId : $accessPointType [$accessPointOrd] >"
}

class AccessPointType
object Input extends AccessPointType {
  override def toString = "INPUT"
}
object Output extends AccessPointType {
  override def toString = "OUTPUT"
}