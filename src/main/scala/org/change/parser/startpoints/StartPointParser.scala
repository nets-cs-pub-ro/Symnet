package org.change.parser.startpoints

import scala.io.Source
import org.change.v2.abstractnet.generic._

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object StartPointParser {

  val startRegex = """([a-zA-Z\-0-9/_\\.]+):([a-zA-Z\-0-9/_\\.]+):([a-zA-Z\-0-9/_\\.]+)""".r

  def parseStarts(file: String): Iterable[(String, String, String)] = {
    val links = Source.fromFile(file).getLines().map(link => link match {
      case startRegex(click, elm, inputPort) =>
        Some((click, elm, inputPort))
      case _ => None
    })

    links.filter(_ != None).flatten.toIterable
  }

}

