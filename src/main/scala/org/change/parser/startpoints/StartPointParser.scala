package org.change.parser.startpoints

import scala.io.Source
import org.change.v2.abstractnet.generic._

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object StartPointParser {

  val startRegex = """(\w+):(\w+):(\d+)""".r

  def parseStarts(file: String): Iterable[(String, String, Int)] = {
    val links = Source.fromFile(file).getLines().map(link => link match {
      case startRegex(click, elm, inputPort) =>
        Some((click, elm, inputPort.toInt))
      case _ => None
    })

    links.filter(_ != None).flatten.toIterable
  }

}

