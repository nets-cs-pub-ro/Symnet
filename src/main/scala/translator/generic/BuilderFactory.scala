package translator.generic

import translator.specific._

object BuilderFactory {

  def getBuilder(nameValue: String, elementType: String) = elementType match {
    case "ToDevice" | "ToNetPort" | "ToNetFront" => ToDevice.getBuilder(nameValue)
    case "FromDevice" | "FromNetPort" | "FromNetFront" => FromDevice.getBuilder(nameValue)
    case "SEQChanger" | "SEQChanger" => SEQChanger.getBuilder(nameValue)
    case "StartTunnel" | "StartTunnel" => StartTunnel.getBuilder(nameValue)
    case "EndTunnel" | "EndTunnel" => EndTunnel.getBuilder(nameValue)
    case "Firewall" | "Firewall" => Firewall.getBuilder(nameValue)
    case "NAT" | "NAT" => NAT.getBuilder(nameValue)
    case "Client" | "Client" => Client.getBuilder(nameValue)
    case "Server" | "Server" => Server.getBuilder(nameValue)
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
    case "Client" | "Client" => Client.getBuilder
    case "Server" | "Server" => Server.getBuilder
    case _ => ID.getBuilder(elementType)
  }
}
