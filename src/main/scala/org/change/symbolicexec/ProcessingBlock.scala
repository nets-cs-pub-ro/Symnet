package org.change.symbolicexec

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
   * @return Fan-in
   */
  def entryCount: Int

  /**
   * @return Fan out count
   */
  def exitCount: Int

  override def toString = s"[ $id in:$entryCount out:$exitCount ]"

}
