package org.change.parser.interclicklinks

import scala.io.Source
import org.change.v2.abstractnet.generic._

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object InterClickLinksParser {

  val linkRegex = """\s*(\w+):(\w+):(\d+)\s*->\s*(\w+):(\w+):(\d+)\s*""".r

  def parseLinks(file: String): Iterable[(String, String, Int, String, String, Int)] = {
    val links = Source.fromFile(file).getLines().map(link => link match {
      case linkRegex(sClick, sElm, sOutputPort, dClick, dElm, dInputPort) =>
        Some((sClick, sElm, sOutputPort.toInt, dClick, dElm, dInputPort.toInt))
      case _ => None
    })

    links.filter(_ != None).flatten.toIterable
  }

}
