package org.change.parser.platformconnection

import org.change.symbolicexec.verifiablemodel._

import scala.io.Source

object PlatformSetupParser {

  def platfromConnections(file: String): List[(String, String)] = {
    val f = Source.fromFile(file)

    (for {
      connection <- f.getLines()
      platforms = connection.split("->")
      if platforms.length > 1
      source = platforms(0).trim
      destination = platforms(1).trim
    } yield (source, destination)).toList
  }

  def platfroms(file: String): List[(String, PlatformType, Int, PlatformPlace)] = {
    val f = Source.fromFile(file)

    (for {
      platform <- f.getLines()
      platforms = platform.split("\\W+")
      p = if (platforms.length > 0) platforms(0).trim else platform.trim
    } yield (p, if (platforms(1) equals "op") Op else Mass, 0, platforms(3) match {
        case "client" => Client
        case "internet" => Internet
        case _ => Middle
      })).toList
  }

}
