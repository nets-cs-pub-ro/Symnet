package org.change.v2.util

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
package object canonicalnames {

  //IP Header offsets
  val IPVersion = 0
  val IPHeaderLength = 4
  val DSCP = 8
  val ECN = 14
  val IPLength = 16
  val IPID = 32
  val IPFlags = 48
  val FragmentOffset = 51
  val TTL = 64
  val Proto = 72
  val HeaderChecksum = 80
  val IPSrc = 96
  val IPDst = 128

  val ICMPProto = 1
  val UDPProto = 17
  val TCPProto = 6

  //UDP header offsets
  val UDPSrc = 0
  val UDPDst = 16
  val UDPLength = 32
  val UDPChecksum = 48

  //TCP header offsets
  val TcpSrc = 0
  val TcpDst = 16
  val TcpSeq = 32
  val TcpAck = 64
  val TcpDataOffset = 96
  val TcpReserved = 100
  val TcpFlags = 103
  val TcpWindowSize = 112
  val TcpChecksum = 128
  val TcpUrgentPointer = 144
  val TcpOptions = 160
  //val TcpPayload = "TCP-Payload"

  val MinPacketSize = 64
}
