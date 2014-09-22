package org.change.symbolicexec

package object types {

  val TcpDumpFields = List("src", "dst", "proto", "port src", "port dst")
  val CanonicalFields = List("IP-Src", "IP-Dst", "Proto", "Port-Src", "Port-Dst")

  val tcpDumpToCanonical = Map(("src","IP-Src"), ("dst","IP-Dst"),
    ("proto","Proto"), ("src port","Port-Src"), ("dst port","Port-Dst"))

}
