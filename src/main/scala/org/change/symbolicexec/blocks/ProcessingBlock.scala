package org.change.symbolicexec.blocks

import org.change.symbolicexec.{Output, PathLocation, Path}

/**
 * A prcessing module implements its functionality by extending this trait.
 */
trait ProcessingBlock {

  /**
   * @return The unique id within a configuration
   */
  def id: String

  /**
   * When execution reaches a processing block it runs its logic and the results are
   * pushed downstream as a list o possible paths.
   * @param p The currently processed path
   * @return Next reachable execution paths
   */
  def process(p: Path): List[Path]

  /**
   * Path forwarding from an input port to an output port.
   * This also ensures
   * @param p
   * @param portOrd
   * @return
   */
  def moveToOutputPort(p: Path, portOrd: Int): Path = p.move(PathLocation(p.location.vmId, id, portOrd, Output))

  /**
   * @return Fan-in
   */
  def entryCount: Int

  /**
   * @return Fan out count
   */
  def exitCount: Int

  override def toString = s"[ $id in:$entryCount out:$exitCount ]"

}
