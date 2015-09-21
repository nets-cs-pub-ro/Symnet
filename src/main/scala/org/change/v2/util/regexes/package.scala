package org.change.v2.util

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
package object regexes {

  val ipv4 = """[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}"""
  val ipv4Regex = ipv4.r

  val ipv4netmask = """[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}/[0-9]{1,2}"""
  val ipv4netmaskRegex = ipv4netmask.r

  val number = """\d+"""
  val numberRegex = number.r

  val portInterval = number + "-" + number
  val portIntervalRegex = portInterval.r
  val portIntervalRegexWithGroups = ("(" + number + ")\\s*-\\s*(" + number + ")").r

  val mac =
    """[0-9A-F][0-9A-F][:-][0-9A-F][0-9A-F][:-][0-9A-F][0-9A-F][:-]
      |[0-9A-F][0-9A-F][:-][0-9A-F][0-9A-F][:-][0-9A-F][0-9A-F]""".stripMargin
  val macRegex = mac.r

  val macCisco ="""[0-9a-f]{4}\.[0-9a-f]{4}\.[0-9a-f]{4}"""
  val macCiscoRegex = macCisco.r
}
