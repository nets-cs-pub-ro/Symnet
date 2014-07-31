package org.change.executor

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
   * @param entryPoint The entry point is not unique since a processing block may have multiple
   *                   input ports, each with a different logic associated
   * @return Next reachable execution paths
   */
  def process(p: Path, entryPoint: Int): List[Path]

}
