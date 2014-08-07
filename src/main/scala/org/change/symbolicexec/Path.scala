package org.change.symbolicexec

/**
 * The execution path is the abstraction used for a given network flow
 * passing through the network.
 */
class Path(history: List[PathLocation], memory: Memory = new Memory()) {

  /**
   * Moving a path requires pushing its new location to the history.
   * @param nextLocation
   * @return
   */
  def move(nextLocation: PathLocation): Path = new Path(nextLocation :: history, memory)

  def modifyWith(f: (Memory) => Memory): Path = new Path(history, f(memory))

  def modifyAndMove(f: (Memory) => Memory, nextLocation: PathLocation) = modifyWith(f).move(nextLocation)

  def valid = memory.valid

  /**
   * The current location of a given path is the head of its history stack.
   * @return
   */
  def location: PathLocation = history.head
  def locationPort = location.accessPointOrd

  override def toString = s"[-> Path (${if (valid) "Valid" else "Invalid"}):\n"+
    s"Symbol Table:\n${memory.mem.map(kv => "->" + (kv._1, kv._2.head.eval).toString).mkString("\n")}\n"+
    s"History:\n${history.mkString("\n")}\n"+
    "->PathEnd]"
}

object Path {

  /**
   * Blank path.
   * @return
   */
  def apply(): Path = new Path(Nil)
}

case class PathLocation(processingBlockId: String, accessPointOrd: Int, accessPointType: AccessPointType) {
  override def toString = s"-> $processingBlockId $accessPointType $accessPointOrd <-"
}

class AccessPointType
object Input extends AccessPointType {
  override def toString = "INPUT"
}
object Output extends AccessPointType {
  override def toString = "OUTPUT"
}