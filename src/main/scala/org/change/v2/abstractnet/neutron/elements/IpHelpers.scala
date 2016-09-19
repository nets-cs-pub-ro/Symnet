package org.change.v2.abstractnet.neutron.elements

object IpHelpers {
  def parseIpAddress(ip : String) : (String, String) = {
    val ipAddr = ip.substring(0, ip.indexOf('/'))
    val ipMask = ip.substring(ip.indexOf('/') + 1)
    (ipAddr, ipMask)
  }
}