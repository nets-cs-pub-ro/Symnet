package org.change.v2.util.canonicalnames

object CanonicalNetworkFields {

  val TcpDumpFields = List("src", "dst", "proto", "port src", "port dst")
  val CanonicalFields = List("IP-Src", "IP-Dst", "Proto", "Port-Src", "Port-Dst")

  val tcpDumpToCanonical = Map(("src","IP-Src"), ("dst","IP-Dst"),
    ("proto","Proto"), ("src port","Port-Src"), ("dst port","Port-Dst"))

}
