package org.change.v2.util.conversion

/**
 * A mapping of some popular protocols to their designated encoding.
 */
object NumberFor {
  private val m = Map(("tcp" -> 6)
    , ("udp" -> 17)
    , ("http" -> 80)
    , ("https" -> 443)
    , ("ssh" -> 22)
  )

  /**
   * If the mapping is not found, 0 is the default.
   * @param s
   * @return
   */
  def apply(s: String): Long = m.get(s) match {
    case Some(v1) => v1
    case None => m.get(s.toLowerCase) match {
      case Some(v2) => v2
      case None => 0
    }
  }
}
