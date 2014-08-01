package org.change.symbolicexec

/**
 * The execution path is the abstraction used for a given network flow
 * passing through the network.
 */
class Path(history: List[PathLocation]) {

  /**
   * Moving a path requires pushing its new location to the history.
   * @param nextLocation
   * @return
   */
  def move(nextLocation: PathLocation): Path = new Path(nextLocation :: history)

  /**
   * The current location of a given path is the head of its history stack.
   * @return
   */
  def location: PathLocation = history.head

  override def toString = s"[-> Path:\nHistory:\n${history.mkString("\n")}->PathEnd]"
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