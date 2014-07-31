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
}

object Path {

  /**
   * Blank path.
   * @return
   */
  def apply(): Path = new Path(Nil)
}

case class PathLocation(processingBlockId: String, accessPointOrd: Int, accessPointType: AccessPointType)

class AccessPointType
object Input extends AccessPointType
object Output extends AccessPointType