package org.change.utils

object RepresentationConversion {

  def ipToNumber(ip: String): Long = {
    ip.split("\\.").map(Integer.parseInt(_)).foldLeft(0L)((a:Long, g:Int)=> a * 256 + g)
  }

  def macToNumber(mac: String): Long = {
    mac.toLowerCase.split(":").map(Integer.parseInt(_, 16)).foldLeft(0L)((a:Long, g:Int)=> a * 256 + g)
  }

  def macToNumberCiscoFormat(mac: String): Long = {
    mac.toLowerCase.split("\\.").map(Integer.parseInt(_, 16)).foldLeft(0L)((a:Long, g:Int)=> a * 65536 + g)
  }

  def ipAndMaskToInterval(ip: String, mask: String): (Long, Long) = {
    val ipv = ipToNumber(ip)
    val maskv = Integer.parseInt(mask)
    val addrS = 32 - maskv
    val lowerM = Long.MaxValue << addrS
    val higherM = Long.MaxValue >>> (maskv + 31)
    (ipv & lowerM, ipv | higherM)
  }

}
