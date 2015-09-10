package org.change.v2.util

import org.change.v2.analysis.memory.Tag

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
package object canonicalnames {

  // Avoid unnecessary tag object creation if we can
  val L4Tag = Tag("L4")
  val L3Tag = Tag("L3")
  val L2Tag = Tag("L2")

  val IPVersionString = "IP-Version"
  val IPSrcString = "IP-Src"
  val IPDstString = "IP-Dst"
  val PortSrcString = "Port-Src"
  val PortDstString = "Port-Dst"
  val L4ProtoString = "Proto"
  val TTLString = "TTL"


  //Ethernet header offsets and fields
  val EtherDstOffset = 0
  val EtherDst = L2Tag + EtherDstOffset
  val EtherSrcOffset = 48
  val EtherSrc = L2Tag + EtherSrcOffset
  val EtherTypeOffset = 96
  val EtherType = L2Tag + EtherTypeOffset
  val PCPOffset = 112
  val PCP = L2Tag + PCPOffset
  val DEIOffset = 115
  val DEI = L2Tag + DEIOffset
  val VLANTagOffset = 116
  val VLANTag = L2Tag + VLANTagOffset

  // TODO: Dunno what is the desc for these
  val EtherProtoIP = 2048
  val EtherProtoVLAN = 33024

  //IP Header offsets
  val IPVersionOffset = 0
  val IPVersion = L3Tag + IPVersionOffset
  val IPHeaderLengthOffset = 4
  val IPHeaderLength = L3Tag + IPHeaderLengthOffset
  val DSCPOffset = 8
  val DSCP = L3Tag + DSCPOffset
  val ECNOffset = 14
  val ECN = L3Tag + ECNOffset
  val IPLengthOffset = 16
  val IPLength = L3Tag + IPLengthOffset
  val IPIDOffset = 32
  val IPID = L3Tag + IPIDOffset
  val IPFlagsOffset = 48
  val IPFlags = L3Tag + IPFlagsOffset
  val FragmentOffsetOffset = 51
  val FragmentOfset = L3Tag + FragmentOffsetOffset
  val TTLOffset = 64
  val TTL = L3Tag + TTLOffset
  val ProtoOffset = 72
  val Proto = L3Tag + ProtoOffset
  val HeaderChecksumOffset = 80
  val HeaderChecksum = L3Tag + HeaderChecksumOffset
  val IPSrcOffset = 96
  val IPSrc = L3Tag + IPSrcOffset
  val IPDstOffset = 128
  val IPDst = L3Tag + IPDstOffset

  // Protocol id numbers
  val ICMPProto = 1
  val UDPProto = 17
  val TCPProto = 6
  val IPIPProto = 94

  //UDP header offsets
  val UDPSrcOffset = 0
  val UDPSrc = L4Tag + UDPSrcOffset
  val UDPDstOffset = 16
  val UDPDst = L4Tag + UDPDstOffset
  val UDPLengthOffset = 32
  val UDPLength = L4Tag + UDPLengthOffset
  val UDPChecksumOffset = 48
  val UDPChecksum = L4Tag + UDPChecksumOffset

  //TCP header offsets
  val TcpSrcOffset = 0
  val TcpSrc = L4Tag + TcpSrcOffset
  val TcpDstOffset = 16
  val TcpDst = L4Tag + TcpDstOffset
  val TcpSeqOffset = 32
  val TcpSeq = L4Tag + TcpSeqOffset
  val TcpAckOffset = 64
  val TcpAck = L4Tag + TcpAckOffset
  val TcpDataOffsetOffset = 96
  val TcpDataOffset = L4Tag + TcpDataOffsetOffset
  val TcpReservedOffset = 100
  val tcpReserved = L4Tag + TcpReservedOffset
  val TcpFlagsOffset = 103
  val TcpFlags = L4Tag + TcpFlagsOffset
  val TcpWindowSizeOffset = 112
  val TcpWindowSize = L4Tag + TcpWindowSizeOffset
  val TcpChecksumOffset = 128
  val TcpChecksum = L4Tag + TcpChecksumOffset
  val TcpUrgentPointerOffset = 144
  val TcpUrgentPointer = L4Tag + TcpUrgentPointerOffset
  val TcpOptionsOffset = 160
  val TcpOptions = L4Tag + TcpOptionsOffset
  //val TcpPayload = "TCP-Payload"

  val MinPacketSize = 64
}
