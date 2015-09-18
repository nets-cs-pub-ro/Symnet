package org.change.symbolicexec

import org.change.v2.util.canonicalnames._

package object types {

  val TcpDumpFields = List("src", "dst", "proto", "port src", "port dst")
  val CanonicalFields = List("IP-Src", "IP-Dst", "Proto", "Port-Src", "Port-Dst")

  val tcpDumpToCanonical = Map(("src", IPSrc), ("dst", IPDst),
    ("proto", Proto), ("src port", TcpSrc), ("dst port", TcpDst))

}
