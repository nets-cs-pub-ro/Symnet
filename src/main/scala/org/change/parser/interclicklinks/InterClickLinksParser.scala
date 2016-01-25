package org.change.parser.interclicklinks

import scala.io.Source
import org.change.v2.abstractnet.generic._
import java.io.File

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object InterClickLinksParser {

  val linkRegex = """\s*([a-zA-Z\-0-9/_]+):([a-zA-Z\-0-9/_]+):([a-zA-Z\-0-9/_]+)\s*->\s*([a-zA-Z\-0-9/_]+):([a-zA-Z\-0-9/_]+):([a-zA-Z\-0-9/_]+)\s*""".r

  def parseLinks(file: String): Iterable[(String, String, String, String, String, String)] = {
    val links = Source.fromFile(file).getLines().map(link => link match {
      case linkRegex(sClick, sElm, sOutputPort, dClick, dElm, dInputPort) =>
        Some((sClick, sElm, sOutputPort, dClick, dElm, dInputPort))
      case _ => None
    })

    links.filter(_ != None).flatten.toIterable
  }

}
