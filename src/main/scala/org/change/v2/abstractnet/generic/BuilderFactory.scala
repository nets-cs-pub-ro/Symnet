package org.change.v2.abstractnet.generic

import org.change.v2.abstractnet.click._

object BuilderFactory {

  def getBuilder(nameValue: String, elementType: String) = elementType match {
    case "ToDevice" | "ToNetPort" | "ToNetFront" => ToDevice.getBuilder(nameValue)
    case "FromDevice" | "FromNetPort" | "FromNetFront" => FromDevice.getBuilder(nameValue)
    case "SEQChanger" => SEQChanger.getBuilder(nameValue)
    case "StartTunnel" => StartTunnel.getBuilder(nameValue)
    case "EndTunnel" => EndTunnel.getBuilder(nameValue)
    case "Firewall" => Firewall.getBuilder(nameValue)
    case "NAT" => NAT.getBuilder(nameValue)
    case "Client" => Client.getBuilder(nameValue)
    case "Server"  => Server.getBuilder(nameValue)
    case "IPRewriter"  => IPRewriter.getBuilder(nameValue)
    case "IPFilter"  => IPFilter.getBuilder(nameValue)
    case _ => ID.getBuilder(nameValue, elementType)
  }

  def getBuilder(elementType: String) = elementType match {
    case "ToDevice" | "ToNetPort" | "ToNetFront" => ToDevice.getBuilder
    case "FromDevice" | "FromNetPort" | "FromNetFront" => FromDevice.getBuilder
    case "SEQChanger" => SEQChanger.getBuilder
    case "StartTunnel" => StartTunnel.getBuilder
    case "EndTunnel" => EndTunnel.getBuilder
    case "Firewall" => Firewall.getBuilder
    case "NAT" => NAT.getBuilder
    case "Client" => Client.getBuilder
    case "Server" => Server.getBuilder
    case "IPRewriter"  => IPRewriter.getBuilder
    case "IPFilter"  => IPFilter.getBuilder
    case _ => ID.getBuilder(elementType)
  }
}
