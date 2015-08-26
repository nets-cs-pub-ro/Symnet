package org.change.symbolicexec.types

object PortType extends NumericType {
  private lazy val _max = (2L << 15) - 1
  override def name = "Port"
  override def max = _max
}

object IP4Type extends NumericType {
  private lazy val _max = (2L << 31) - 1
  override def name = "IPv4"
  override def max = _max
}

object MACType extends NumericType {
  private lazy val _max = (2L << 47) - 1
  override def name = "MAC"
  override def max = _max
}

object VLANType extends NumericType {
  private lazy val _max = (2L << 15) - 1
  override def name = "VLAN"
  override def max = _max
}

object ProtoType extends NumericType {
  private lazy val _max = (2L << 7) - 1
  override def name = "Protocol"
  override def max = _max
}

object TypeUtils {

  def canonicalForSymbol(s: String): NumericType = s match {
    case "IP-Src" | "IP-Dst" | "IP" => IP4Type
    case "Port-Src" | "Port-Dst" | "Port" => PortType
    case "Proto" | "proto" => ProtoType
    case "VLAN" | "vlan" => VLANType
    case "MAC-Src" | "MAC-dst" => MACType
    case _ => NumericType()
  }

}

