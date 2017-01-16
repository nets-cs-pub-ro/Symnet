package org.change.parser.switch

import java.io.File

import scala.io.Source

/**
  * Created by radu on 15.01.2017.
  */
object TrivialSwitchTable {

  def parseMacFile(f: File): Traversable[(String, String)] = {
    val stream = Source.fromFile(f)

    (for {
      l <- stream.getLines()
      parts = l.trim.split("\\s+")
      mac = parts(0)
      port = parts(1)
    } yield {
      (port, mac)
    }).toTraversable
  }

}
