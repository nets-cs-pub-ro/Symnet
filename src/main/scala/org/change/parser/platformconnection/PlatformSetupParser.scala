package org.change.parser.platformconnection

import org.change.symbolicexec.verifiablemodel.{Op, Mass, PlatformType}

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

  def platfroms(file: String): List[(String, PlatformType)] = {
    val f = Source.fromFile(file)

    (for {
      platform <- f.getLines()
      platforms = platform.split("\\W+")
      p = if (platforms.length > 0) platforms(0).trim else platform.trim
    } yield (p, if (platforms.length > 1) Op else Mass)).toList
  }

}
